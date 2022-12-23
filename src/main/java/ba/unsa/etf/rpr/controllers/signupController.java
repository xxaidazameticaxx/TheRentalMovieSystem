package ba.unsa.etf.rpr.controllers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import ba.unsa.etf.rpr.dao.UsersDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Users;


public class signupController {
    public Button signupButton1_id;
    public PasswordField passwordField1_id;
    public TextField usernameField1_id;


    @FXML
    public void initialize() {
        usernameField1_id.getStyleClass().add("fieldCorrect");
        usernameField1_id.getStyleClass().add("fieldNotCorrect");
        passwordField1_id.getStyleClass().add("fieldCorrect");
        passwordField1_id.getStyleClass().add("fieldNotCorrect");

        usernameField1_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (usernameField1_id.getText().trim().isEmpty() || usernameField1_id.getText().contains(" ")) {
                    usernameField1_id.getStyleClass().removeAll("fieldCorrect");
                    usernameField1_id.getStyleClass().add("fieldNotCorrect");
                } else {
                    usernameField1_id.getStyleClass().removeAll("fieldNotCorrect");
                    usernameField1_id.getStyleClass().add("fieldCorrect");
                }
            }
        });

        passwordField1_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (passwordField1_id.getText().trim().isEmpty() || passwordField1_id.getText().contains(" ")) {
                    passwordField1_id.getStyleClass().removeAll("fieldCorrect");
                    passwordField1_id.getStyleClass().add("fieldNotCorrect");
                } else {
                    passwordField1_id.getStyleClass().removeAll("fieldNotCorrect");
                    passwordField1_id.getStyleClass().add("fieldCorrect");
                }
            }
        });
    }

    public void signupclick1(ActionEvent actionEvent) {
    }
}
