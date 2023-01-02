package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

/**
 * Dao interface for Users domain bean
 *
 * @author Aida Zametica
 */
public interface UsersDao extends Dao<Users> {

    Users getUserByUsernameAndPassword(String username, String password) throws TheMovieRentalSystemException;
}
