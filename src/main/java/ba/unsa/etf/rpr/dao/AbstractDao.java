package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;

import java.sql.*;
import java.util.*;

/**
 * Abstract class that implements core DAO CRUD methods for every entity
 *
 * @author Aida Zametica
 */
public abstract class AbstractDao <T extends Idable>implements Dao<T> {
    private Connection connection;
    private String tableName;

    public AbstractDao(String tableName){
        try{
            this.tableName = tableName;
            Properties p = new Properties();
            p.load(ClassLoader.getSystemResource("db.properties").openStream());
            String url = p.getProperty("url");
            String username = p.getProperty("user");
            String password = p.getProperty("password");
            this.connection = DriverManager.getConnection(url, username, password);
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

}
