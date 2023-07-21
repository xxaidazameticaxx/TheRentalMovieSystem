package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.util.List;

/**
 * Business Logic Layer for Movies
 * @author Aida Zametica
 */
public class MoviesManager {

    public Movies getById(int id) throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().getById(id);
    }


    public Movies add(Movies m) throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().add(m);
    }


    public void update(Movies m) throws TheMovieRentalSystemException {
        DaoFactory.moviesDao().update(m);
    }


    public void delete(int id) throws TheMovieRentalSystemException {
        DaoFactory.moviesDao().delete(id);
    }

    public List<Movies> getAll() throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().getAll();
    }

    public List<Movies> filterByGenre(String genre) throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().filterByGenre(genre);
    }

    public List<Movies> searchByLanguage(String language) throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().searchByLanguage(language);
    }

    public List<Movies> getUserIssuedMovies(Users user) throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().getUserIssuedMovies(user);
    }

    public List<Movies> searchByMovie_name(String movie_name) throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().searchByMovie_name(movie_name);
    }


}
