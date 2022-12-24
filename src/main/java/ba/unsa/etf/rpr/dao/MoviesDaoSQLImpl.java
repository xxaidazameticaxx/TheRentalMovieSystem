package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.io.FileReader;
import java.sql.*;
import java.util.*;

public class MoviesDaoSQLImpl extends AbstractDao<Movies> implements MoviesDao{
    private Connection conn;

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

    //metoda koja bi trebala da izbaci sve info za filmove kad searchas po kategoriji REFACTOR??
    @Override
    public List<Movies> searchByGenre(String genre) throws MovieException{
        return executeQuery("SELECT * FROM MOVIES WHERE genre = ?",new Object[]{genre});
    }

    //REFACTOR??
    @Override
    public List<Movies> getRentedMovies() throws MovieException{
        return executeQuery("SELECT * FROM MOVIES WHERE id = (SELECT r.movie_id FROM RENTS r WHERE r.return_date IS NOT NULL",null);
    }

    //REFACTOR??
    @Override
    public List<Movies> searchByLanguage(String language) throws MovieException {
        return executeQuery("SELECT * FROM MOVIES WHERE language = ?",new Object[]{language});
    }

    //REFACTOR parametar bio int user_id
    @Override
    public List<Movies> getUserIssuedMovies(Users user) throws MovieException{
       return executeQuery("SELECT * FROM MOVIES WHERE id = (SELECT r.movie_id FROM RENTS r WHERE r.id = ? AND r.return_date IS NOT NULL)",new Object[]{user.getId()});
    }


}
