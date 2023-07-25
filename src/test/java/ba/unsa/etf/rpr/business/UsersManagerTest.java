package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import org.junit.jupiter.api.Test;

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
    void getUserByUsernameAndPasswordTest() throws TheMovieRentalSystemException {
        UsersManager usersManager = new UsersManager();

        Users testUser = new Users();
        testUser.setAdmin(false);
        testUser.setUsername("Test User");
        testUser.setPassword("Test Password");

        usersManager.add(testUser);

        assertEquals(testUser.getId(),usersManager.getUserByUsernameAndPassword("Test User","Test Password").getId());

        usersManager.delete(testUser.getId());

    }



}