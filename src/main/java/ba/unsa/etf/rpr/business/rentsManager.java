package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.util.List;

/**
 * Business Logic Layer for Rents
 * @author Aida Zametica
 */
public class rentsManager {
    public Rents getById(int id) throws MovieException {
        return DaoFactory.rentsDao().getById(id);
    }

    public Rents add(Rents r) throws MovieException{
        return DaoFactory.rentsDao().add(r);
    }

    public void update(Rents r) throws MovieException{
        DaoFactory.rentsDao().update(r);
    }

    void delete(int id) throws MovieException{
        DaoFactory.rentsDao().delete(id);
    }

    List<Rents> getAll() throws MovieException{
        return DaoFactory.rentsDao().getAll();
    }

}
