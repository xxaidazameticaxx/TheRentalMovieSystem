package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserAccountController {

    public TextField passwordField;
    public Button saveChangesButton;
    public TextField usernameField;
    private final UsersManager usersManager = new UsersManager();

    public void initialize() {

        passwordField.setEditable(true);
        usernameField.setEditable(false);

        passwordField.setText(LoginController.getPasswordField());
        usernameField.setText(LoginController.getUsernameField());

    }

    public void saveAccountChanges() throws TheMovieRentalSystemException {
        Users u = usersManager.getUserByUsernameAndPassword(LoginController.getUsernameField(), LoginController.getPasswordField());
        u.setPassword(passwordField.getText());
        u.setUsername(usernameField.getText());

        try{
            if(passwordField.getText().length()<6) throw new TheMovieRentalSystemException("Password");
            usersManager.update(u);
            LoginController.setUsernameField(usernameField.getText());
            LoginController.setPasswordField(passwordField.getText());
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
        }

    }
}
