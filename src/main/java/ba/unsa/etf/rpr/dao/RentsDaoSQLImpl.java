package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.io.FileReader;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class RentsDaoSQLImpl extends AbstractDao<Rents> implements RentsDao{
    public RentsDaoSQLImpl(){
       super("RENTS");
    }


    //refactor?????
    public List<Rents> getUserByRentId(int rent_id) throws MovieException {
        return executeQuery("SELECT * FROM USERS WHERE id = (SELECT r.user_id FROM RENTS r WHERE r.id = ?)",new Object[]{rent_id});
    }

    @Override
    public Rents row2object(ResultSet rs) throws MovieException {
        try {
            Rents rent = new Rents();
            rent.setId(rs.getInt("id"));
            rent.setMovie(DaoFactory.moviesDao().getById(rs.getInt("movie_id"))); //REFACTOR UPITAN
            rent.setUser(DaoFactory.usersDao().getById(rs.getInt("user_id")));  //REFACTOR UPITAN
            rent.setRent_date(rs.getDate("rent_date"));
            rent.setReturn_date(rs.getDate("return_date"));
            return rent;
        } catch (Exception e) {
            throw new MovieException(e.getMessage(), e);
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

}
