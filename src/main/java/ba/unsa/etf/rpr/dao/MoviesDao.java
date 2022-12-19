package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;

import java.util.List;

/**
 * Dao interface for Movies domain bean
 *
 * @author Aida Zametica
 */

public interface MoviesDao extends Dao<Movies>{

    //metoda koja bi trebala da izbaci sve info za filmove kad searchas po žanru
    List<Movies> searchByGenre(String genre);

    List<Movies> searchByLanguage(String language);
}
