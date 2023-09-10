package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.sql.*;
import java.util.*;

public class RentsDaoSQLImpl extends AbstractDao<Rents> implements RentsDao{

    public RentsDaoSQLImpl(){
       super("RENTS");
    }

    @Override
    public Rents row2object(ResultSet rs) throws TheMovieRentalSystemException {
        try {
            Rents rent = new Rents();
            rent.setId(rs.getInt("id"));
            rent.setMovie(DaoFactory.moviesDao().getById(rs.getInt("movie_id")));
            rent.setUser(DaoFactory.usersDao().getById(rs.getInt("user_id")));
            rent.setRent_date(rs.getDate("rent_date"));
            rent.setReturn_date(rs.getDate("return_date"));
            return rent;
        } catch (Exception e) {
            throw new TheMovieRentalSystemException(e.getMessage(), e);
        }
    }

    /**
     * @param object - object to be mapped
     * @return map representation of object
     */
    @Override
    public Map<String, Object> object2row(Rents object) {
        Map<String, Object> item = new TreeMap<>();
        item.put("id", object.getId());
        item.put("movie_id", object.getMovie().getId());
        item.put("user_id", object.getUser().getId());
        item.put("rent_date", object.getRent_date());
        item.put("return_date", object.getReturn_date());

        return item;
    }

    /**
     * Retrieves a list of rents representing movies issued by the specified user.
     * @param user The user whose issued movies are to be retrieved.
     * @return A List of Rents representing the movies issued by the specified user.
     * @throws TheMovieRentalSystemException If an error occurs while executing the query.
     */
    @Override
    public List<Rents> getUserIssuedMovies(Users user) throws TheMovieRentalSystemException {
        return executeQuery("SELECT * FROM RENTS WHERE user_id = ?",new Object[]{user.getId()});
    }

    @Override
    public List<Object[]> getRentsIssuedByOthers(Users user) throws TheMovieRentalSystemException {
        String query = "SELECT user_id, movie_id FROM RENTS WHERE movie_id IN (SELECT movie_id FROM RENTS WHERE user_id = ?) AND user_id != ?";
        List<Object[]> rentsByOthers = new ArrayList<>();

        try (
                PreparedStatement statement = getConnection().prepareStatement(query);
        ) {
            statement.setInt(1, user.getId());
            statement.setInt(2, user.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    int movieId = resultSet.getInt("movie_id");
                    Object[] rentInfo = { userId, movieId };
                    rentsByOthers.add(rentInfo);
                }
            }

            return rentsByOthers;
        } catch (SQLException e) {
            throw new TheMovieRentalSystemException("Error retrieving rents issued by others", e);
        }
    }




}
