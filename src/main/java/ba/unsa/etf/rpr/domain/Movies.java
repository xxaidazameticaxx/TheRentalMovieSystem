package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * bean for movies
 * @author Aida Zametica
 */
public class Movies implements Idable{
    private int id;
    private String movie_name;
    private String genre;
    private int duration;
    private double ratings;
    private int release_year;
    private String language;
    private double price;

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

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
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
        return id == movies.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie_name, price, genre, duration, ratings, release_year, language);
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movie_id=" + id +
                ", movie_name='" + movie_name + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", ratings=" + ratings +
                ", release_year=" + release_year +
                ", language='" + language + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

}
