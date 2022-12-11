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
