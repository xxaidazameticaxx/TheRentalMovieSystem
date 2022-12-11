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
    public Rents getById(int rent_id) {
        String query = "SELECT * FROM rents WHERE rent_id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, rent_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){ // result set is iterator.
                Rents rent = new Rents();
                rent.setRent_id(rs.getInt("rent_id"));
                rent.setMovie_id(rs.getInt("movie_id"));
                rent.setUser_id(rs.getInt("user_id"));
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
        return null;
    }

    @Override
    public Rents update(Rents item) {
        return null;
    }

    @Override
    public void delete(int rent_id) {
        String insert = "DELETE FROM rents WHERE rent_id = ?";
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
        return null;
    }
}
