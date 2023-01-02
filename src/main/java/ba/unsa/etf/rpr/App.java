package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.MoviesDaoSQLImpl;
import ba.unsa.etf.rpr.dao.RentsDaoSQLImpl;
import ba.unsa.etf.rpr.dao.UsersDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.MovieException;

import java.sql.Date;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
     Users m = new Users();
     m.setUsername("admin");
     m.setPassword("admin");
     m.setAdmin(true);

     UsersDaoSQLImpl filmic = new UsersDaoSQLImpl();
     filmic.add(m);

    }
}