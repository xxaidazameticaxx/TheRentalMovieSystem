package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RentsDaoSQLImpl implements RentsDao{
    private Connection conn;

    public RentsDaoSQLImpl(){
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

    public Movies getMovieByRentId(int rent_id) {
        String query = "SELECT * FROM MOVIES WHERE movie_id = (SELECT r.movie_id FROM RENTS r WHERE r.id = ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, rent_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Movies movie = new Movies();
                movie.setMovie_id(rs.getInt("movie_id"));
                movie.setMovie_name(rs.getString("movie_name"));
                movie.setPrice(rs.getDouble("price"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRatings(rs.getDouble("ratings"));
                movie.setRelease_date(rs.getDate("release_date"));
                movie.setLanguage(rs.getString("language"));
                rs.close();
                return movie;
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

    public Users getUserByRentId(int rent_id) {
        String query = "SELECT * FROM USERS WHERE user_id = (SELECT r.user_id FROM RENTS r WHERE r.id = ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, rent_id);
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
    public Rents getById(int rent_id) {
        String query = "SELECT * FROM RENTS WHERE rent_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, rent_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Rents rent = new Rents();
                rent.setRent_id(rs.getInt("rent_id"));
                rent.setMovie(getMovieByRentId(rent_id)); // rent.setMovie(new MoviesDaoSQLImpl().getById(rs.getInt("movie_id"))); PROVJERITI
                rent.setUser(getUserByRentId(rent_id));   //rent.setUser(new UsersDaoSQLImpl().getById(rs.getInt("user_id"))); PROVJERITI
                rent.setRent_date(rs.getDate("rent_date"));
                rent.setReturn_date(rs.getDate("return_date"));
                rs.close();
                return rent;
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
    public Rents add(Rents item) {
        String insert = "INSERT INTO RENTS(rent_date,return_date,movie,user) VALUES (?,?,?,?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(insert,Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, (Date) item.getRent_date());
            stmt.setDate(2, (Date) item.getReturn_date());
            stmt.setInt(3,item.getMovie().getMovie_id());
            stmt.setInt(4,item.getUser().getUser_id());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next(); // we know that there is one key
            item.setRent_id(rs.getInt(1)); //set id to return it back
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Rents update(Rents item) {
        String query = "UPDATE RENTS SET rent_date = ?, return_date = ?, movie = ?, user = ? WHERE rent_id=?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setDate(1,(Date) item.getRent_date());
            stmt.setDate(2, (Date) item.getReturn_date()); //zasto ja moram (Date) pisati??
            stmt.setInt(3,item.getMovie().getMovie_id());
            stmt.setInt(4,item.getUser().getUser_id());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            System.out.println("Problem pri radu sa bazom podataka");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(int rent_id) {
        String insert = "DELETE FROM RENTS WHERE rent_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, rent_id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Rents> getAll() {
        String query = "SELECT * FROM RENTS";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Rents> rentsLista = new ArrayList<>();
            while (rs.next()) {
                Rents rent = new Rents();
                rent.setRent_id(rs.getInt("rent_id"));
                rent.setRent_date(rs.getDate("rent_date"));
                rent.setReturn_date(rs.getDate("return_date"));
                rent.setMovie(new MoviesDaoSQLImpl().getById(rs.getInt("movie_id"))); //PROVJERITI
                rent.setUser(new UsersDaoSQLImpl().getById(rs.getInt("user_id")));    //PROVJERITI

                rentsLista.add(rent);

            }
            rs.close();
            return rentsLista;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
