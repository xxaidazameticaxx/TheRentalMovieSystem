package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MoviesManagerTest {

    /**
     *
     * Checks if the new movie was added correctly by checking if it exists in the list
     * @throws TheMovieRentalSystemException
     */
    @Test
    void add() throws TheMovieRentalSystemException {
        MoviesManager moviesManager = new MoviesManager();

        // Mock the MoviesManager to control the behavior of validateMovie
        MoviesManager moviesManagerMock = mock(MoviesManager.class);
        when(moviesManagerMock.validateMovie(any(), any(), any(), any(), any(), any(), any())).thenReturn(true);

        String movieName = "Test Movie";
        String durationStr = "120";
        String genre = "Test Genre";
        String ratingsStr = "7.5";
        String priceStr = "5.99";
        String language = "Test Language";
        String releaseYearStr = "2001";

        Movies addedMovie = moviesManager.add(movieName, durationStr, genre, ratingsStr, priceStr, language, releaseYearStr);
        List<Movies> allMovies = moviesManager.getAll();

        boolean isMovieAdded = allMovies.contains(addedMovie);

        Assertions.assertTrue(isMovieAdded);

        // Clean up by deleting the added movie
        moviesManager.delete(addedMovie.getId());
    }

    /**
     * Searches for a movie by its name and verifies the result.
     */
    @Test
    void searchByMovieName() throws TheMovieRentalSystemException {
        MoviesManager moviesManager = new MoviesManager();

        String movieName = "Test Movie";
        String durationStr = "120";
        String genre = "Test Genre";
        String ratingsStr = "7.5";
        String priceStr = "5.99";
        String language = "Test Language";
        String releaseYearStr = "2001";

        Movies addedMovie = moviesManager.add(movieName, durationStr, genre, ratingsStr, priceStr, language, releaseYearStr);

        // Search for the movie by its name
        List<Movies> searchResults = moviesManager.searchByMovie_name("Test Movie");

        // Assert that the search returned only one result
        assertEquals(1, searchResults.size());

        // Assert that the search result is the same movie that was added
        assertEquals(addedMovie, searchResults.get(0));

        // Clean up by deleting the movie from the MoviesManager
        moviesManager.delete(addedMovie.getId());
    }

    /**
     * Filters movies by genre and verifies the results.
     * @throws TheMovieRentalSystemException
     */
    @Test
    void filterByGenre() throws TheMovieRentalSystemException {
        MoviesManager moviesManager = new MoviesManager();

        String movieName = "Test Movie";
        String durationStr = "120";
        String genre = "Test Genre";
        String ratingsStr = "7.5";
        String priceStr = "5.99";
        String language = "Test Language";
        String releaseYearStr = "2001";

        Movies movie1 = moviesManager.add(movieName, durationStr, genre, ratingsStr, priceStr, language, releaseYearStr);
        Movies movie2 = moviesManager.add(movieName, durationStr, genre, ratingsStr, priceStr, language, releaseYearStr);

        // Search for the movie by its name
        List<Movies> filterResults = moviesManager.filterByGenre("Test Genre");

        // Assert that the search returned only two result
        assertEquals(2, filterResults.size());

        // Assert that the search result is the same movie that was added
        assertEquals(movie1, filterResults.get(0));

        // Assert that the search result is the same movie that was added
        assertEquals(movie2, filterResults.get(1));

        // Clean up by deleting the movies from the MoviesManager
        moviesManager.delete(movie1.getId());
        moviesManager.delete(movie2.getId());
    }

    /**
     * Validates movie data with various scenarios.
     */
    @Test
    void testValidateMovie() {
        MoviesManager moviesManager = new MoviesManager();

        // Valid movie data
        boolean valid = moviesManager.validateMovie("Test Movie", "120", "Test Genre", "7.5", "5.99", "Test Language", "2001");
        Assertions.assertTrue(valid);

        // Invalid movie data: Empty movieName
        valid = moviesManager.validateMovie("", "120", "Test Genre", "7.5", "5.99", "Test Language", "2001");
        Assertions.assertFalse(valid);

        // Invalid movie data: Negative duration
        valid = moviesManager.validateMovie("Test Movie", "-120", "Test Genre", "7.5", "5.99", "Test Language", "2001");
        Assertions.assertFalse(valid);

        // Invalid movie data: Empty genre
        valid = moviesManager.validateMovie("Test Movie", "120", "", "7.5", "5.99", "Test Language", "2001");
        Assertions.assertFalse(valid);

        // Invalid movie data: Ratings above the limit
        valid = moviesManager.validateMovie("Test Movie", "120", "Test Genre", "11.5", "5.99", "Test Language", "2001");
        Assertions.assertFalse(valid);

        // Invalid movie data: Negative price
        valid = moviesManager.validateMovie("Test Movie", "120", "Test Genre", "7.5", "-5.99", "Test Language", "2001");
        Assertions.assertFalse(valid);

        // Invalid movie data: Empty language
        valid = moviesManager.validateMovie("Test Movie", "120", "Test Genre", "7.5", "5.99", "", "2001");
        Assertions.assertFalse(valid);

        // Invalid movie data: Negative release year
        valid = moviesManager.validateMovie("Test Movie", "120", "Test Genre", "7.5", "5.99", "Test Language", "-2001");
        Assertions.assertFalse(valid);
    }
}