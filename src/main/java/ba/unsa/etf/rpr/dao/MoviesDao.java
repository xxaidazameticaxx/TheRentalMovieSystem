package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.util.List;

/**
 * Dao interface for Movies domain bean
 *
 * @author Aida Zametica
 */

public interface MoviesDao extends Dao<Movies>{

    List<Movies> filterByGenre(String genre) throws TheMovieRentalSystemException;

    List<Movies> searchByMovie_name(String movie_name) throws TheMovieRentalSystemException;
}
