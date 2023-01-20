package ba.unsa.etf.rpr.dao;

import java.util.Properties;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton pattern for database connection
 *
 * @author Aida Zametica
 */
public class DBConnection {
    private static DBConnection instance = null;
    private Connection connection = null;
    private DBConnection() {}

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        if (connection == null) {
            try {
                Properties p = new Properties();
                p.load(ClassLoader.getSystemResource("db1.properties").openStream());
                String url = p.getProperty("url");
                String username = p.getProperty("user");
                String password = p.getProperty("password");
                connection = DriverManager.getConnection(url, username, password);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
      }
    }
