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
     * provjeriti da li radi
     * @param username
     * @param password
     * @return
     * @throws TheMovieRentalSystemException
     */
    @Override
    public Users getUserByUsernameAndPassword(String username, String password) throws TheMovieRentalSystemException {
        return executeQueryUnique("SELECT * FROM USERS WHERE username = ? AND password = ?",new Object[]{username,password});
    }

}
