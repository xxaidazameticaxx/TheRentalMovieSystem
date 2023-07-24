package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.util.*;
import java.sql.*;

public class UsersDaoSQLImpl extends AbstractDao<Users> implements UsersDao{

    public UsersDaoSQLImpl(){
        super("USERS");
    }

    /**
     *
     * @param rs - result set from database
     * @return
     * @throws TheMovieRentalSystemException
     */
    @Override
    public Users row2object(ResultSet rs) throws TheMovieRentalSystemException {
        try {
            Users user = new Users();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAdmin(rs.getBoolean("admin"));
            return user;
        } catch (Exception e) {
            throw new TheMovieRentalSystemException(e.getMessage(), e);
        }
    }

    /**
     * @param object - object to be mapped
     * @return map representation of object
     */
    @Override
    public Map<String, Object> object2row(Users object) {
        Map<String, Object> item = new TreeMap<>();
        item.put("id", object.getId());
        item.put("username", object.getUsername());
        item.put("password", object.getPassword());
        item.put("admin", object.isAdmin());
        return item;
    }


    /**
     * Retrieves the user with the specified username and password from the database.
     * @param username The username of the user to be retrieved.
     * @param password The password of the user to be retrieved.
     * @return The Users object representing the user with the specified username and password.
     * @throws TheMovieRentalSystemException If an error occurs while executing the query or if no user is found with the given credentials.
     */
    @Override
    public Users getUserByUsernameAndPassword(String username, String password) throws TheMovieRentalSystemException {
        return executeQueryUnique("SELECT * FROM USERS WHERE username = ? AND password = ?",new Object[]{username,password});
    }

    /**
     * Retrieves a list of users with the specified username from the database.
     * @param username The username of the users to be retrieved.
     * @return A List of Users representing the users with the specified username.
     * @throws TheMovieRentalSystemException If an error occurs while executing the query.
     */
    @Override
    public List<Users> getUsersByUsername(String username) throws TheMovieRentalSystemException {
        return executeQuery("SELECT * FROM USERS WHERE username = ?",new Object[]{username});
    }

}
