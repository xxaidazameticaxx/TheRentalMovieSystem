package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersManagerTest {

    @Test
    void update() throws TheMovieRentalSystemException {

        UsersManager usersManager = new UsersManager();

        Users testUser = new Users();
        testUser.setId(100);
        testUser.setAdmin(false);
        testUser.setUsername("Test User");
        testUser.setPassword("Test Password");

        usersManager.add(testUser);

        testUser.setAdmin(true);
        usersManager.update(testUser);

        // Retrieve the user from the manager (simulating a database fetch)
        Users updatedUser = usersManager.getById(testUser.getId());

        // Check if the user status has been updated to true
        assertTrue(updatedUser.isAdmin());

        usersManager.delete(testUser.getId());

    }

    @Test
    void getUserByUsernameAndPassword() throws TheMovieRentalSystemException {
        UsersManager usersManager = new UsersManager();

        Users testUser = new Users();
        testUser.setAdmin(false);
        testUser.setUsername("Test User");
        testUser.setPassword("Test Password");

        usersManager.add(testUser);

        assertEquals(testUser.getId(),usersManager.getUserByUsernameAndPassword("Test User","Test Password").getId());

        usersManager.delete(testUser.getId());

    }

    /**
     * in GUI i didnt allow users to have the same username but for the purpose of testing the search bar in the admin users menu
     * @throws TheMovieRentalSystemException
     */
    @Test
    void getUsersByUsername() throws TheMovieRentalSystemException {

        UsersManager usersManager = new UsersManager();

        Users testUser1 = new Users();
        testUser1.setAdmin(false);
        testUser1.setUsername("Test User");
        testUser1.setPassword("Test Password1");

        usersManager.add(testUser1);

        Users testUser2 = new Users();
        testUser2.setAdmin(false);
        testUser2.setUsername("Test User");
        testUser2.setPassword("Test Password2");

        usersManager.add(testUser2);

        List<Users> filterResults = usersManager.getUsersByUsername("Test User");

        Assertions.assertEquals(2, filterResults.size());

        Assertions.assertEquals(testUser1, filterResults.get(0));
        Assertions.assertEquals(testUser2, filterResults.get(1));

        usersManager.delete(testUser1.getId());
        usersManager.delete(testUser2.getId());

    }

    /**
     * non-existent id
     */
    @Test
    void getByid() {
        assertThrows(TheMovieRentalSystemException.class, ()->{new MoviesManager().getById(999);});
    }

}