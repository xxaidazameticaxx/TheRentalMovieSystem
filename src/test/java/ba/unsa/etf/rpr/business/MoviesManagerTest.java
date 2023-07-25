package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MoviesManagerTest {

    /**
     * Checks if the new movie was added correctly by checking if it exists in the list
     * @throws TheMovieRentalSystemException
     */
    @Test
    void add() throws TheMovieRentalSystemException {

        MoviesManager moviesManager = new MoviesManager();

        Movies movie = new Movies();
        movie.setRelease_year(0);
        movie.setMovie_name("Test Movie");
        movie.setPrice(0.0);
        movie.setDuration(0);
        movie.setGenre("Test Genre");
        movie.setRatings(0);
        movie.setLanguage("Test Language");

        moviesManager.add(movie);

        boolean isMovieAdded = moviesManager.getAll().contains(movie);
        Assertions.assertTrue(isMovieAdded);

        moviesManager.delete(movie.getId());

    }

    /**
     *
     * @throws TheMovieRentalSystemException
     */
    @Test
    void searchByMovie_name() throws TheMovieRentalSystemException {
        MoviesManager moviesManager = new MoviesManager();

        Movies movie = new Movies();
        movie.setRelease_year(0);
        movie.setMovie_name("Test Movie");
        movie.setPrice(0.0);
        movie.setDuration(0);
        movie.setGenre("Test Genre");
        movie.setRatings(0);
        movie.setLanguage("Test Language");

        moviesManager.add(movie);

        // Search for the movie by its name
        List<Movies> searchResults = moviesManager.searchByMovie_name("Test Movie");

        // Assert that the search returned only one result
        Assertions.assertEquals(1, searchResults.size());

        // Assert that the search result is the same movie that was added
        Assertions.assertEquals(movie, searchResults.get(0));

        // Clean up by deleting the movie from the MoviesManager
        moviesManager.delete(movie.getId());
    }

    /**
     *
     * @throws TheMovieRentalSystemException
     */
    @Test
    void filterByGenre() throws TheMovieRentalSystemException {
        MoviesManager moviesManager = new MoviesManager();

        //made up genre
        Movies movie1 = new Movies();
        movie1.setRelease_year(0);
        movie1.setMovie_name("Test Movie");
        movie1.setPrice(0.0);
        movie1.setDuration(0);
        movie1.setGenre("Test Genre");
        movie1.setRatings(0);
        movie1.setLanguage("Test Language");

        //made up genre
        Movies movie2 = new Movies();
        movie2.setRelease_year(0);
        movie2.setMovie_name("Test Movie");
        movie2.setPrice(0.0);
        movie2.setDuration(0);
        movie2.setGenre("Test Genre");
        movie2.setRatings(0);
        movie2.setLanguage("Test Language");

        //Add movie1 and movie 2
        moviesManager.add(movie1);
        moviesManager.add(movie2);


        // Search for the movie by its name
        List<Movies> filterResults = moviesManager.filterByGenre("Test Genre");

        // Assert that the search returned only two result
        Assertions.assertEquals(2, filterResults.size());

        // Assert that the search result is the same movie that was added
        Assertions.assertEquals(movie1, filterResults.get(0));

        // Assert that the search result is the same movie that was added
        Assertions.assertEquals(movie2, filterResults.get(1));

        // Clean up by deleting the movies from the MoviesManager
        moviesManager.delete(movie1.getId());
        moviesManager.delete(movie2.getId());
    }
}