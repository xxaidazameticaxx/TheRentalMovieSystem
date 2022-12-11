package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;

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
        String insert = "INSERT INTO movies(movie_name,price,genre,duration,ratings,release_date,language) VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getMovie_name());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, item.getGenre());
            stmt.setInt(4,item.getDuration());
            stmt.setDouble(5,item.getRatings());
            stmt.setDate(6, (Date) item.getRelease_date());
            stmt.setString(7, item.getLanguage());
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
        return null;
    }

    @Override
    public void delete(int id) {

    }
    @Override
    public List<Movies> getAll() {
        return null;
    }
}
