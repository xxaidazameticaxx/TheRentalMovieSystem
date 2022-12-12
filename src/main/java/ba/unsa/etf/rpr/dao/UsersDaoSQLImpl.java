package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Users;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Properties;

public class UsersDaoSQLImpl implements UsersDao{
    private Connection conn;

    public UsersDaoSQLImpl(){
        try{
            FileReader fr = new FileReader("src/main/resources/db.properties");
            Properties p = new Properties();
            p.load(fr);
            String url = p.getProperty("url");
            String user = p.getProperty("user");
            String password = p.getProperty("password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url,user,password);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Users getById(int user_id) {
        String query = "SELECT * FROM USERS WHERE user_id = ?";
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
        String insert = "INSERT INTO USERS(username,password,admin) VALUES(?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getUsername());
            stmt.setString(2, item.getPassword());
            stmt.setBoolean(3, item.isAdmin());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next(); // we know that there is one key
            item.setUser_id(rs.getInt(1)); //set id to return it back
            return item;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Users update(Users item) {
        String insert = "UPDATE USERS SET username = ?,password = ?, admin = ? WHERE user_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, item.getUsername());
            stmt.setObject(2, item.getPassword());
            stmt.setObject(3, item.isAdmin());
            stmt.setObject(4, item.getUser_id());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void delete(int user_id) {
        String insert = "DELETE FROM USERS WHERE user_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, user_id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Users> getAll() {
        String query = "SELECT * FROM USERS";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Users> usersLista = new ArrayList<>();
            while (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                usersLista.add(user);
            }
            rs.close();
            return usersLista;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
