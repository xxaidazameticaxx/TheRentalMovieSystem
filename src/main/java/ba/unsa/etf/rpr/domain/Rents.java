package ba.unsa.etf.rpr.domain;

import java.util.Date;
import java.util.Objects;

/**
 * holds basic information about the rents
 *
 * @author Aida Zametica
 */
public class Rents implements Idable{
    private int id;
    private Date rent_date;
    private Date return_date = null;
    private Movies movie;
    private Users user;

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getRent_date() {
        return rent_date;
    }

    public void setRent_date(Date rent_date) {
        this.rent_date = rent_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rents rents = (Rents) o;
        return id == rents.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rent_date, return_date, movie, user);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Rents{" +
                "rent_id=" + id +
                ", rent_date=" + rent_date +
                ", return_date=" + return_date +
                ", movie=" + movie +
                ", user=" + user +
                '}';
    }
}
