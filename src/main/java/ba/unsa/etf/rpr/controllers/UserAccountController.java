package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;

public class UserAccountController {

    public TextField passwordField;
    public Button saveChangesButton;
    public TextField usernameField;
    private final UsersManager usersManager = new UsersManager();

    /**
     * Initializes the UserAccountController by setting the initial values for the username and password fields.
     * The username field is set to the current logged-in user's username, and the password field is editable and set to the current password.
     */
    public void initialize() {

        passwordField.setEditable(true);
        usernameField.setEditable(false);

        passwordField.setText(LoginController.getPasswordField());
        usernameField.setText(LoginController.getUsernameField());

    }

    /**
     * Handles the "Save Changes" button click event for updating the user's account information.
     * It validates the new password and updates the user's password in the system if it meets the requirements.
     * Displays appropriate success or error messages for the password update.
     *
     * @throws TheMovieRentalSystemException If the new password is too short (less than 6 characters).
     */
    public void saveAccountChanges() throws TheMovieRentalSystemException, NoSuchAlgorithmException {
        Users u = usersManager.getUserByUsernameAndPassword(LoginController.getUsernameField(), UsersManager.hashPassword(LoginController.getPasswordField()));
        u.setPassword(UsersManager.hashPassword(passwordField.getText()));
        u.setUsername(usernameField.getText());

        try{
            if(passwordField.getText().length()<6) throw new TheMovieRentalSystemException("Password");
            usersManager.update(u);
            LoginController.setUsernameField(usernameField.getText());
            LoginController.setPasswordField(UsersManager.hashPassword(passwordField.getText()));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successfully changed");
            alert.setHeaderText("You can now log into your account using the new password!");
            alert.showAndWait();

        }catch(TheMovieRentalSystemException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage()+" too short!");
            alert.setContentText(e.getMessage()+" must contain at least 6 characters.");
            alert.showAndWait();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
