package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class adminMenuController {
    public TextField welcomeTextField1_id;
    public Button moviesButton_id;
    public Button rentsButton_id;
    public Button usersButton_id;


    public void setWelcomeTextField1_id(String welcomeAdminText) {

        welcomeTextField1_id.setText(welcomeAdminText);
    }


    public void clickMovies(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/moviesAdmin.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Calculate the position of the stage
            double x = (screenBounds.getWidth() - 900) / 2;
            double y = (screenBounds.getHeight() - 600) / 2;

        // Set the stage position
            stage.setX(x);
            stage.setY(y);
            stage.show();
    }

    public void clickRents(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/rentsAdmin.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Calculate the position of the stage
        double x = (screenBounds.getWidth() - 900) / 2;
        double y = (screenBounds.getHeight() - 600) / 2;

        // Set the stage position
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

    public void clickUsers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/usersAdmin.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Calculate the position of the stage
        double x = (screenBounds.getWidth() - 900) / 2;
        double y = (screenBounds.getHeight() - 600) / 2;

        // Set the stage position
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }
}
