package ba.unsa.etf.rpr.dao;


/**
 * Factory method for singleton implementation of DAOs
 *
 * @author Aida Zametica
 */
public class DaoFactory {
    private static final MoviesDao moviesDao = new MoviesDaoSQLImpl();
    private static final UsersDao usersDao = new UsersDaoSQLImpl();
    private static final RentsDao rentsDao = new RentsDaoSQLImpl();

    private DaoFactory(){
    }

    public static MoviesDao moviesDao(){
        return moviesDao;
    }

    public static UsersDao usersDao(){
        return usersDao;
    }

    public static RentsDao rentsDao(){
        return rentsDao;
    }
}
