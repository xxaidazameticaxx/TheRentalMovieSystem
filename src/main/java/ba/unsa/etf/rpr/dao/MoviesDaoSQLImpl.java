package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;

import java.sql.*;
import java.util.List;

public class MoviesDaoSQLImpl implements MoviesDao{
    private Connection conn;

    public MoviesDaoSQLImpl(){
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/root","root","root");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Movies getById(int movie_id) {
        String query = "SELECT * FROM movies WHERE movie_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, movie_id);
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

    @Override
    public Movies add(Movies item) {
        String insert = "INSERT INTO movies(movie_name,genre,duration,ratings,release_date,language,price) VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getMovie_name());
            stmt.setString(2, item.getGenre());
            stmt.setInt(3,item.getDuration());
            stmt.setDouble(4,item.getRatings());
            stmt.setDate(5, (Date) item.getRelease_date());
            stmt.setString(6, item.getLanguage());
            stmt.setDouble(7, item.getPrice());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next(); // we know that there is one key
            item.setMovie_id(rs.getInt(1)); //set id to return it back
            return item;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Movies update(Movies item) {
        String insert = "UPDATE movies SET movie_name = ?, genre= ?, duration = ?, ratings = ?, release_date = ?, language = ?, price = ? WHERE movie_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, item.getMovie_name());
            stmt.setObject(2, item.getGenre());
            stmt.setObject(3, item.getDuration());
            stmt.setObject(4, item.getRatings());
            stmt.setObject(5, item.getRelease_date());
            stmt.setObject(6, item.getLanguage());
            stmt.setObject(7, item.getPrice());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(int movie_id) {
        String insert = "DELETE FROM movies WHERE movie_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, movie_id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Movies> getAll() {
        return null;
    }
}
