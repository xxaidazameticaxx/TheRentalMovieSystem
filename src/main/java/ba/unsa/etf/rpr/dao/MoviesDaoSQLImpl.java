package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import java.sql.*;
import java.util.*;

public class MoviesDaoSQLImpl extends AbstractDao<Movies> implements MoviesDao{

    public MoviesDaoSQLImpl(){
        super("MOVIES");
    }

    /**
     * @param rs - result set from database
     * @return
     * @throws TheMovieRentalSystemException
     */
    @Override
    public Movies row2object(ResultSet rs) throws TheMovieRentalSystemException {
        try {
            Movies movie = new Movies();
            movie.setId(rs.getInt("id"));
            movie.setMovie_name(rs.getString("movie_name"));
            movie.setPrice(rs.getDouble("price"));
            movie.setGenre(rs.getString("genre"));
            movie.setDuration(rs.getInt("duration"));
            movie.setRatings(rs.getDouble("ratings"));
            movie.setRelease_year(rs.getInt("release_year"));
            movie.setLanguage(rs.getString("language"));
            return movie;
        } catch (Exception e) {
            throw new TheMovieRentalSystemException(e.getMessage(), e);
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
        item.put("release_year",object.getRelease_year());
        item.put("language",object.getLanguage());
        return item;
    }

    /**
     * Filters movies by the specified genre.
     * @param genre The genre to filter the movies by.
     * @return A List of Movies that belong to the specified genre.
     * @throws TheMovieRentalSystemException If an error occurs while executing the query.
     */
    @Override
    public List<Movies> filterByGenre(String genre) throws TheMovieRentalSystemException {
        return executeQuery("SELECT * FROM MOVIES WHERE genre = ?",new Object[]{genre});
    }

    /**
     * Searches for movies by the specified movie name.
     * @param movie_name The movie name to search for.
     * @return A List of Movies that match the specified movie name.
     * @throws TheMovieRentalSystemException  If an error occurs while executing the query.
     */
    @Override
    public List<Movies> searchByMovie_name(String movie_name) throws TheMovieRentalSystemException {
        return executeQuery("SELECT * FROM MOVIES WHERE movie_name = ?",new Object[]{movie_name});
    }

    @Override
    public List<Movies> getRecommendedMovies(Users user) throws TheMovieRentalSystemException {
        return executeQuery("SELECT * FROM MOVIES WHERE id IN (SELECT movie_id from RENTS WHERE user_id IN( SELECT user_id from RENTS where user_id IN(SELECT user_id FROM RENTS WHERE movie_id IN (SELECT movie_id FROM RENTS WHERE user_id = ?) )) and movie_id NOT IN (SELECT movie_id FROM RENTS WHERE user_id = ?)) ORDER BY ratings DESC LIMIT 3;",new Object[]{user.getId(),user.getId()});
    }

    @Override
    public List<Movies> getRandomMovies(Users user) throws TheMovieRentalSystemException {
        return executeQuery(" SELECT * FROM MOVIES WHERE id NOT IN (SELECT movie_id FROM RENTS WHERE user_id = ?) ORDER BY RAND();",new Object[]{user.getId()});
    }





}
