package ba.unsa.etf.rpr.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    
    private Stage stage;
    private Scene scene;
    public Button signupButton_id;
    public Button loginButton_id;
    public TextField usernameField_id;
    public PasswordField passwordField_id;


    @FXML
    public void initialize() {
        usernameField_id.getStyleClass().add("poljeNijeIspravno");
        passwordField_id.getStyleClass().add("poljeNijeIspravno");
        usernameField_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (usernameField_id.getText().trim().isEmpty()) {
                    usernameField_id.getStyleClass().removeAll("fieldCorrect");
                    usernameField_id.getStyleClass().add("fieldNotCorrect");
                } else {
                    usernameField_id.getStyleClass().removeAll("fieldNotCorrect");
                    usernameField_id.getStyleClass().add("fieldCorrect");
                }
            }
        });

        passwordField_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (passwordField_id.getText().trim().isEmpty()) {
                    passwordField_id.getStyleClass().removeAll("fieldCorrect");
                    passwordField_id.getStyleClass().add("fieldNotCorrect");
                } else {
                    passwordField_id.getStyleClass().removeAll("fieldNotCorrect");
                    passwordField_id.getStyleClass().add("fieldCorrect");
                }
            }
        });
    }

    public void loginclick(ActionEvent actionEvent) throws IOException {
        if(usernameField_id.getText().isEmpty()){
            usernameField_id.getStyleClass().add("fieldNotCorrect");
            return;
        }
        if(passwordField_id.getText().isEmpty()){
            passwordField_id.getStyleClass().add("fieldNotCorrect");
            return;
        }

        if(passwordField_id.getText().equals("admin") && usernameField_id.getText().equals("admin")) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminMenu.fxml"));
            stage.setTitle("Admin Menu");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }
        else{
            /*Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/userMenu.fxml"));
            stage.setTitle("User Menu");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            /*
             */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userMenu.fxml"));
            Parent root = loader.load();
            userMenuController uMC = loader.getController();
            uMC.setWelcomeTextField_id("Welcome " + usernameField_id.getText());
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }



    }
}
