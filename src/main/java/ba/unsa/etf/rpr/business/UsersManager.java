package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Business Logic Layer for Users
 * @author Aida Zametica
 */
public class UsersManager {

    public Users getById(int id) throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().getById(id);
    }

    public Users add(Users u) throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().add(u);
    }

    public void update(Users u) throws TheMovieRentalSystemException {
        DaoFactory.usersDao().update(u);
    }

    public void delete(int id) throws TheMovieRentalSystemException {
        DaoFactory.usersDao().delete(id);
    }

    public List<Users> getAll() throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().getAll();
    }
    public Users getUserByUsernameAndPassword(String username, String password) throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().getUserByUsernameAndPassword(username,password);
    }

    public List<Users> getUsersByUsername(String username) throws TheMovieRentalSystemException {
        return DaoFactory.usersDao().getUsersByUsername(username);
    }

    private static final String HASHING_ALGORITHM = "SHA-256";

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
        messageDigest.update(password.getBytes());
        byte[] hashPassword = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashPassword)
            stringBuilder.append(String.format("%02x",b));
        return stringBuilder.toString();
    }


}
