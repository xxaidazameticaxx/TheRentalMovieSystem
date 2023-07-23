package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class UserMenuController {

    public TextField welcomeTextField_id;
    public Button myMoviesButton_id;
    public Button allMoviesButton_id;
    public ImageView informationUser_id;
    public ImageView userAccount_id;

    public void setWelcomeTextField_id(String welcomeUserText) {

        welcomeTextField_id.setText(welcomeUserText);
    }

    public void allMoviesClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/moviesUser.fxml")));
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

    public void myMoviesClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/myMoviesUser.fxml")));
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

    public void informationClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/userInformation.fxml")));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/icon.jpg") );
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accountClick(MouseEvent mouseEvent) {
    }
}
