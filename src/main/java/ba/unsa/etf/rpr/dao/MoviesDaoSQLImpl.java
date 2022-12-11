package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class MoviesDaoSQLImpl implements MoviesDao{

    private Connection conn;

    public MoviesDaoSQLImpl(){
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/root","root","root");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public Movies getById(int id) {
        return null;
    }

    @Override
    public Movies add(Movies item) {
        return null;
    }

    @Override
    public Movies update(Movies item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Movies> getAll() {
        return null;
    }
}
