package ba.unsa.etf.rpr.domain;

import java.util.Date;
import java.util.Objects;

/**
 * bean for movies
 * @author Aida Zametica
 */
public class Movies {
    private int movie_id;
    private String movie_name;
    private String genre;
    private int duration;
    private double ratings;
    private Date release_date;
    private String language;
    private double price;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movies movies = (Movies) o;
        return movie_id == movies.movie_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie_id, movie_name, price, genre, duration, ratings, release_date, language);
    }
}
