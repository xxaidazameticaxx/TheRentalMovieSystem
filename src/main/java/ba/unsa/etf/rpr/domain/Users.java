package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * Holds login details of all users
 *
 * @author Aida Zametica
 */
public class Users implements Idable{
    private int user_id;
    private String username;
    private String password;
    private boolean admin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return user_id == users.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, password, admin);
    }

    @Override
    public void setId(int id) {
        this.user_id = id;
    }

    @Override
    public int getId() {
        return user_id;
    }

}
