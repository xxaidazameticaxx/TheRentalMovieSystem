package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Movies;
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

    public Movies add(String movieName, String durationStr, String genre, String ratingsStr, String priceStr, String language, String releaseYearStr) throws TheMovieRentalSystemException {
        if (validateMovie(movieName,durationStr,genre,ratingsStr,priceStr,language,releaseYearStr)) {
            Movies movie = new Movies();
            movie.setMovie_name(movieName);
            movie.setDuration(Integer.parseInt(durationStr));
            movie.setGenre(genre);
            movie.setRatings(Double.parseDouble(ratingsStr));
            movie.setPrice(Double.parseDouble(priceStr));
            movie.setLanguage(language);
            movie.setRelease_year(Integer.parseInt(releaseYearStr));
            return DaoFactory.moviesDao().add(movie);
        } else {
            throw new TheMovieRentalSystemException("Invalid movie data");
        }
    }

    public boolean validateMovie(String movieName, String durationStr, String genre, String ratingsStr, String priceStr, String language, String releaseYearStr) {
        return
                !movieName.isEmpty() &&
                        Integer.parseInt(durationStr) > 0 &&
                !genre.isEmpty() &&
                        Double.parseDouble(ratingsStr) >= 0 && Double.parseDouble(ratingsStr) <= 10
                        && Double.parseDouble(priceStr) >= 0 &&
                        !language.isEmpty() &&
                Integer.parseInt(releaseYearStr) > 0;
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

    public List<Movies> searchByMovie_name(String movie_name) throws TheMovieRentalSystemException {
        return DaoFactory.moviesDao().searchByMovie_name(movie_name);
    }


}
