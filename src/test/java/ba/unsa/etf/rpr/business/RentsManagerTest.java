package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;


class RentsManagerTest {

    @Test
    void getUserIssuedMovies() throws TheMovieRentalSystemException {

        RentsManager rentManager = new RentsManager();

        // Create a test user
        Users testUser = new Users();
        testUser.setId(100); // Set the user's ID
        testUser.setUsername("xxx");
        testUser.setPassword("xxx");
        testUser.setAdmin(false);

        UsersManager usersManager = new UsersManager();

        usersManager.add(testUser);

        MoviesManager moviesManager = new MoviesManager();

        String movieName = "Test Movie";
        String durationStr = "120";
        String genre = "Test Genre";
        String ratingsStr = "7.5";
        String priceStr = "5.99";
        String language = "Test Language";
        String releaseYearStr = "2001";

        Movies testMovie = moviesManager.add(movieName, durationStr, genre, ratingsStr, priceStr, language, releaseYearStr);

        // Create some sample rents issued by the test user
        Rents rent1 = new Rents();
        rent1.setUser(testUser);
        rent1.setMovie(testMovie);
        rent1.setRent_date(new Date());

        Rents rent2 = new Rents();
        rent2.setUser(testUser);
        rent2.setMovie(testMovie);
        rent2.setRent_date(new Date());

        // Add the rents to the RentManager's internal data store (e.g., database or list)
        rentManager.add(rent1);
        rentManager.add(rent2);

        // Call the method being tested
        List<Rents> issuedMovies = rentManager.getUserIssuedMovies(testUser);

        // Assert that the size of the returned list matches the number of rents issued by the test user
        Assertions.assertEquals(2, issuedMovies.size());

        // Assert that the returned list contains the expected rents
        Assertions.assertTrue(issuedMovies.contains(rent1));
        Assertions.assertTrue(issuedMovies.contains(rent2));

        rentManager.delete(rent1.getId());
        rentManager.delete(rent2.getId());
        usersManager.delete(testUser.getId());
        moviesManager.delete(testMovie.getId());

    }

}
