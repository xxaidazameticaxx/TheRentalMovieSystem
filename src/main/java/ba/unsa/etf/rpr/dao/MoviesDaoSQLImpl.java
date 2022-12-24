package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Movies;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MoviesDaoSQLImpl implements MoviesDao{
    private Connection conn;

    public MoviesDaoSQLImpl(){
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
    public Movies getById(int id) {
        String query = "SELECT * FROM MOVIES WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Movies movie = new Movies();
                movie.setId(rs.getInt("id"));
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

    @Override
    public Movies add(Movies item) {
        String insert = "INSERT INTO MOVIES(movie_name,genre,duration,ratings,release_date,language,price) VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getMovie_name());
            stmt.setString(2, item.getGenre());
            stmt.setInt(3,item.getDuration());
            stmt.setDouble(4,item.getRatings());
            stmt.setDate(5, item.getRelease_date());
            stmt.setString(6, item.getLanguage());
            stmt.setDouble(7, item.getPrice());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next(); // we know that there is one key
            item.setId(rs.getInt(1)); //set id to return it back
            return item;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Movies update(Movies item) {
        String insert = "UPDATE MOVIES SET movie_name = ?, genre= ?, duration = ?, ratings = ?, release_date = ?, language = ?, price = ? WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, item.getMovie_name());
            stmt.setObject(2, item.getGenre());
            stmt.setObject(3, item.getDuration());
            stmt.setObject(4, item.getRatings());
            stmt.setObject(5, item.getRelease_date());
            stmt.setObject(6, item.getLanguage());
            stmt.setObject(7, item.getPrice());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(int id) {
        String insert = "DELETE FROM MOVIES WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Movies> getAll() {
        String query = "SELECT * FROM MOVIES";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Movies> moviesLista = new ArrayList<>();
            while (rs.next()) {
                Movies movie = new Movies();
                movie.setId(rs.getInt("id"));
                movie.setMovie_name(rs.getString("movie_name"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRatings(rs.getDouble("ratings"));
                movie.setRelease_date(rs.getDate("release_date"));
                movie.setLanguage(rs.getString("language"));
                movie.setPrice(rs.getDouble("price"));
                moviesLista.add(movie);
            }
            rs.close();
            return moviesLista;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //metoda koja bi trebala da izbaci sve info za filmove kad searchas po kategoriji
    @Override
    public List<Movies> searchByGenre(String genre) {
        String query = "SELECT * FROM MOVIES WHERE genre = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Movies>movieLista = new ArrayList<>();
            while (rs.next()){ // result set is iterator.
                Movies movie = new Movies();
                movie.setId(rs.getInt("id"));
                movie.setMovie_name(rs.getString("movie_name"));
                movie.setPrice(rs.getDouble("price"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRatings(rs.getDouble("ratings"));
                movie.setRelease_date(rs.getDate("release_date"));
                movie.setLanguage(rs.getString("language"));
                movieLista.add(movie);
            }
            return movieLista;
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
        return null;
    }

    //mozda?????????
    @Override
    public List<Movies> getRentedMovies() {
        String query = "SELECT * FROM MOVIES WHERE id = (SELECT r.movie_id FROM RENTS r WHERE r.return_date IS NOT NULL";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Movies>movieLista = new ArrayList<>();
            while (rs.next()){ // result set is iterator.
                Movies movie = new Movies();
                movie.setId(rs.getInt("id"));
                movie.setMovie_name(rs.getString("movie_name"));
                movie.setPrice(rs.getDouble("price"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRatings(rs.getDouble("ratings"));
                movie.setRelease_date(rs.getDate("release_date"));
                movie.setLanguage(rs.getString("language"));
                movieLista.add(movie);
            }
            return movieLista;
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
        return null;
    }

    @Override
    public List<Movies> searchByLanguage(String language) {
        String query = "SELECT * FROM MOVIES WHERE language = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, language);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Movies>movieLista = new ArrayList<>();
            while (rs.next()){ // result set is iterator.
                Movies movie = new Movies();
                movie.setId(rs.getInt("id"));
                movie.setMovie_name(rs.getString("movie_name"));
                movie.setPrice(rs.getDouble("price"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRatings(rs.getDouble("ratings"));
                movie.setRelease_date(rs.getDate("release_date"));
                movie.setLanguage(rs.getString("language"));
                movieLista.add(movie);
            }
            return movieLista;
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
        return null;
    }

    //mozda???????? URADJEN REFACTOR AL ONO...
    @Override
    public List<Movies> getUserIssuedMovies(int user_id) {
        String query = "SELECT * FROM MOVIES WHERE id = (SELECT r.movie_id FROM RENTS r WHERE r.id = ? AND r.return_date IS NOT NULL)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Movies>movieLista = new ArrayList<>();
            while (rs.next()){ // result set is iterator.
                Movies movie = new Movies();
                movie.setId(rs.getInt("id"));
                movie.setMovie_name(rs.getString("movie_name"));
                movie.setPrice(rs.getDouble("price"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRatings(rs.getDouble("ratings"));
                movie.setRelease_date(rs.getDate("release_date"));
                movie.setLanguage(rs.getString("language"));
                movieLista.add(movie);
            }
            return movieLista;
        }
        catch (SQLException e){
            e.printStackTrace(); // poor error handling
        }
        return null;
    }


}
