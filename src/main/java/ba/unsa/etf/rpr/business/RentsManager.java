package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.util.List;

/**
 * Business Logic Layer for Rents
 * @author Aida Zametica
 */
public class RentsManager {

    public Rents getById(int id) throws TheMovieRentalSystemException {
        return DaoFactory.rentsDao().getById(id);
    }

    public Rents add(Rents r) throws TheMovieRentalSystemException {
        return DaoFactory.rentsDao().add(r);
    }

    public void update(Rents r) throws TheMovieRentalSystemException {
        DaoFactory.rentsDao().update(r);
    }

    public void delete(int id) throws TheMovieRentalSystemException {
        DaoFactory.rentsDao().delete(id);
    }

    public List<Rents> getUserIssuedMovies(Users user) throws TheMovieRentalSystemException {
        return DaoFactory.rentsDao().getUserIssuedMovies(user);
    }

    public List<Rents> getAll() throws TheMovieRentalSystemException {
        return DaoFactory.rentsDao().getAll();
    }

}
