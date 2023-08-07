package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UsersManagerTest {

    /*
     * Verifies if the user deletion is correctly delegated to the manager.
     */
    @Test
    void testDelete() throws TheMovieRentalSystemException {
        UsersManager usersManagerMock = mock(UsersManager.class);
        int userId = 123;
        usersManagerMock.delete(userId);
        verify(usersManagerMock).delete(userId);
    }

   /*
    * Verifies if a user's admin status can be successfully updated.
    */
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

    /*
     * Verifies if a user can be successfully retrieved using their username and password.
     */
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
     * in GUI, I did not allow users to have the same username but for the purpose of testing the search bar in the admin users menu
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
     *  Verifies if the system correctly throws TheMovieRentalSystemException for a non-existent ID.
     */
    @Test
    void getById() {
        assertThrows(TheMovieRentalSystemException.class, ()->{new MoviesManager().getById(999);});
    }

}