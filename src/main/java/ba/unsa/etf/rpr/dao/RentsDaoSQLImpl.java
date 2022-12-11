package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;

import java.sql.*;
import java.util.List;

public class RentsDaoSQLImpl implements RentsDao{
    private Connection conn;

    public RentsDaoSQLImpl(){
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/root","root","root");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Movies getMovieByRentId(int rent_id) {
        String query = "SELECT * FROM movies WHERE movie_id = (SELECT r.movie_id FROM rents r WHERE r.id = ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, rent_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Movies movie = new Movies();
                movie.setMovie_id(rs.getInt("movie_id"));
                movie.setMovie_name(rs.getString("movie_name"));
                movie.setPrice(rs.getDouble("price"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRatings(rs.getDouble("ratings"));
                movie.setRelease_date(rs.getDate("release_date"));
                movie.setLanguage(rs.getString("language"));
                rs.close();
                return movie;
            }
            else{
                return null; // if there is no elements in the result set return null
            }
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
        return null;
    }

    public Users getUserByRentId(int rent_id) {
        String query = "SELECT * FROM users WHERE user_id = (SELECT r.user_id FROM rents r WHERE r.id = ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, rent_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                rs.close();
                return user;
            }
            else{
                return null; // if there is no elements in the result set return null
            }
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
        return null;
    }

    @Override
    public Rents getById(int rent_id) {
        String query = "SELECT * FROM rents WHERE rent_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, rent_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Rents rent = new Rents();
                rent.setRent_id(rs.getInt("rent_id"));
                rent.setMovie(getMovieByRentId(rent_id)); //rent.setMovie(new MoviesDaoSQLImpl().getById(rs.getInt("movie_id")));
                rent.setUser(getUserByRentId(rent_id));   //rent.setUser(new UsersDaoSQLImpl().getById(rs.getInt("user_id")));
                rent.setRent_date(rs.getDate("rent_date"));
                rent.setReturn_date(rs.getDate("return_date"));
                rs.close();
                return rent;
            }
            else{
                return null; // if there is no elements in the result set return null
            }
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
        return null;
    }


    @Override
    public Rents add(Rents item) {
        return null;
    }

    @Override
    public Rents update(Rents item) {
        return null;
    }

    @Override
    public void delete(int rent_id) {
        String insert = "DELETE FROM rents WHERE rent_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, rent_id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Rents> getAll() {
        return null;
    }
}
