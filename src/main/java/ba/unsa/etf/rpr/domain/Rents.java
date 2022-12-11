package ba.unsa.etf.rpr.domain;

import java.util.Date;
import java.util.Objects;

public class Rents {
    private int rent_id;
    private int movie_id;
    private int user_id;
    private Date rent_date;
    private Date return_date;

    public int getRent_id() {
        return rent_id;
    }

    public void setRent_id(int rent_id) {
        this.rent_id = rent_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
        return rent_id == rents.rent_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rent_id, movie_id, user_id, rent_date, return_date);
    }

}
