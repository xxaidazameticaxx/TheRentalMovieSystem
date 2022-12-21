package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    public Button signupButton_id;
    public Button loginButton_id;

    public void loginclick(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userMenu.fxml"));
        stage.setTitle("UserMenu");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
