package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Users;


import java.util.List;
import java.sql.*;

public class UsersDaoSQLImpl implements UsersDao{
    private Connection conn;

    public UsersDaoSQLImpl(){
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/root","root","root");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Users getById(int id) {
        return null;
    }

    @Override
    public Users add(Users item) {
        return null;
    }

    @Override
    public Users update(Users item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Users> getAll() {
        return null;
    }
}
