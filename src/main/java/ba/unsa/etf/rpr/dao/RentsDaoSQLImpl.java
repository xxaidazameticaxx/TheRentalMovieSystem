package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Rents;

import java.sql.*;
import java.util.List;

public class RentsDaoSQLImpl implements RentsDao{
    private Connection conn;

    public RentsDaoSQLImpl(){
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/root","root","root");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Rents getById(int id) {
        return null;
    }

    @Override
    public Rents add(Rents item) {
        return null;
    }

    @Override
    public Rents update(Rents item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Rents> getAll() {
        return null;
    }
}
