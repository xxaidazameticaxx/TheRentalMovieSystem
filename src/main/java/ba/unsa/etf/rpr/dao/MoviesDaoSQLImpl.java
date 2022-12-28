package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.sql.*;
import java.util.*;

public class MoviesDaoSQLImpl extends AbstractDao<Movies> implements MoviesDao{

    public MoviesDaoSQLImpl(){
        super("MOVIES");
    }

    @Override
    public Movies row2object(ResultSet rs) throws MovieException {
        try {
            Movies movie = new Movies();
            movie.setId(rs.getInt("id"));
            movie.setMovie_name(rs.getString("movie_name"));
            movie.setPrice(rs.getDouble("price"));
            movie.setGenre(rs.getString("genre"));
            movie.setDuration(rs.getInt("duration"));
            movie.setRatings(rs.getDouble("ratings"));
            movie.setRelease_date(rs.getDate("release_date"));
            movie.setLanguage(rs.getString("language"));
            return movie;
        } catch (Exception e) {
            throw new MovieException(e.getMessage(), e);
        }
    }

    /**
     * @param object - object to be mapped
     * @return map representation of object
     */
    @Override
    public Map<String, Object> object2row(Movies object) {
        Map<String, Object> item = new TreeMap<>();
        item.put("id", object.getId());
        item.put("movie_name",object.getMovie_name());
        item.put("price",object.getPrice());
        item.put("genre",object.getGenre());
        item.put("duration",object.getDuration());
        item.put("ratings",object.getRatings());
        item.put("release_date",object.getRelease_date());
        item.put("language",object.getLanguage());
        return item;
    }

    //koja je svrha ove metode Aida?
    @Override
    public List<Movies> getRentedMovies() throws MovieException{
        return executeQuery("SELECT * FROM MOVIES INNER JOIN RENTS ON MOVIES.id = RENTS.movie_id AND RENTS.return_date IS NULL",null);
    }

    @Override
    public List<Movies> searchByGenre(String genre) throws MovieException{
        return executeQuery("SELECT * FROM MOVIES WHERE genre = ?",new Object[]{genre});
    }

    @Override
    public List<Movies> searchByLanguage(String language) throws MovieException {
        return executeQuery("SELECT * FROM MOVIES WHERE language = ?",new Object[]{language});
    }

    @Override
    public List<Movies> getUserIssuedMovies(Users user) throws MovieException{
       return executeQuery("SELECT * FROM MOVIES WHERE id IN (SELECT r.movie_id FROM RENTS r WHERE user_id = ?)",new Object[]{user.getId()});
    }

    @Override
    public List<Movies> searchByMovie_name(String movie_name) throws MovieException {
        return executeQuery("SELECT * FROM MOVIES WHERE movie_name = ?",new Object[]{movie_name});
    }


}
