package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.util.List;
import java.util.Map;


/**
 * Dao interface for Rents domain bean
 *
 * @author Aida Zametica
 */

public interface RentsDao extends Dao<Rents>{

    List<Rents> getUserIssuedMovies(Users user) throws TheMovieRentalSystemException;

    List<Object[]> getRentsIssuedByOthers(Users user) throws TheMovieRentalSystemException;
}
