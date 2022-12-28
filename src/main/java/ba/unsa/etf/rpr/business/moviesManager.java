package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.Dao;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.util.List;

/**
 * Business Logic Layer for Movies
 * @author Aida Zametica
 */
public class moviesManager {

    public Movies getById(int id) throws MovieException{
        return DaoFactory.moviesDao().getById(id);
    }


    public Movies add(Movies m) throws MovieException{
        return DaoFactory.moviesDao().add(m);
    }


    public void update(Movies m) throws MovieException{
        DaoFactory.moviesDao().update(m);
    }


    void delete(int id) throws MovieException{
        DaoFactory.moviesDao().delete(id);
    }

    List<Movies> getAll() throws MovieException{
        return DaoFactory.moviesDao().getAll();
    }

    public List<Movies> searchByGenre(String genre) throws MovieException {
        return DaoFactory.moviesDao().searchByGenre(genre);
    }

    public List<Movies> searchByLanguage(String language) throws MovieException {
        return DaoFactory.moviesDao().searchByLanguage(language);
    }

    public List<Movies> getUserIssuedMovies(Users user) throws MovieException{
        return DaoFactory.moviesDao().getUserIssuedMovies(user);
    }

    public List<Movies> searchByMovie_name(String movie_name) throws MovieException {
        return DaoFactory.moviesDao().searchByMovie_name(movie_name);
    }


}
