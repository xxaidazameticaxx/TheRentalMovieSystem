package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.util.List;

/**
 * Dao interface for Movies domain bean
 *
 * @author Aida Zametica
 */

public interface MoviesDao extends Dao<Movies>{

    List<Movies> searchByGenre(String genre) throws MovieException;

    List<Movies> getRentedMovies() throws MovieException;

    List<Movies> searchByLanguage(String language) throws MovieException;

    List<Movies> getUserIssuedMovies(Users user) throws MovieException;

    //List<Movies> getUserIssuedMovies(int user_id);
}
