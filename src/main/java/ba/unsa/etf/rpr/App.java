package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.MoviesDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Movies;

import java.sql.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MoviesDaoSQLImpl filmic = new MoviesDaoSQLImpl();
        Movies m = new Movies();
        m.setDuration(125);
        m.setMovie_name("Vlak u snijegu");
        m.setRelease_date(new Date(2001,14,12));
        m.setLanguage("bosanski");
        m.setGenre("ljubavni");
        m.setRatings(7.4);
        filmic.add(m);
        List<Movies>mm = filmic.getAll();
        for(Movies x: mm) System.out.println(x);

    }
}