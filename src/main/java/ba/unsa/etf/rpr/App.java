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

        RentsDaoSQLImpl rentic = new RentsDaoSQLImpl();
        MoviesDaoSQLImpl filmic = new MoviesDaoSQLImpl();
        UsersDaoSQLImpl useric = new UsersDaoSQLImpl();

        Rents rent = new Rents();
        rent.setUser(useric.getById(1));
        rent.setMovie(filmic.getById(17));
        rent.setReturn_date(new Date(2009,2,1));
        rent.setRent_date(new Date(2001,3,12));

        rentic.add(rent);

        List<Movies>lista = filmic.getRentedMovies();
        for(Movies x:lista) System.out.println(x);

        System.out.println();

        List<Rents>lista1 = rentic.getAll();
        for(Rents x:lista1) System.out.println(x);




    }
}