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
    public Users getById(int user_id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                rs.close();
                return user;
            }
            else{
                return null; // if there is no elements in the result set return null
            }
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
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