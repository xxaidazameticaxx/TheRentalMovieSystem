package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.util.List;

/**
 * Business Logic Layer for Users
 * @author Aida Zametica
 */
public class usersManager {
    public Users getById(int id) throws MovieException {
        return DaoFactory.usersDao().getById(id);
    }

    public Users add(Users u) throws MovieException{
        return DaoFactory.usersDao().add(u);
    }

    public void update(Users u) throws MovieException{
        DaoFactory.usersDao().update(u);
    }

    void delete(int id) throws MovieException{
        DaoFactory.usersDao().delete(id);
    }

    List<Users> getAll() throws MovieException{
        return DaoFactory.usersDao().getAll();
    }
    public Users getUserByUsernameAndPassword(String username, String password) throws MovieException {
        return DaoFactory.usersDao().getUserByUsernameAndPassword(username,password);
    }


}
