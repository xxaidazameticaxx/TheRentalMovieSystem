package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.util.List;

/**
 * Business Logic Layer for Users
 * @author Aida Zametica
 */
public class usersManager {
    public Users getById(int id) throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().getById(id);
    }

    public Users add(Users u) throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().add(u);
    }

    public void update(Users u) throws TheMovieRentalSystemException {
        DaoFactory.usersDao().update(u);
    }

    void delete(int id) throws TheMovieRentalSystemException {
        DaoFactory.usersDao().delete(id);
    }

    List<Users> getAll() throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().getAll();
    }
    public Users getUserByUsernameAndPassword(String username, String password) throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().getUserByUsernameAndPassword(username,password);
    }


}
