package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class userMenuController {
    public TextField welcomeTextField_id;
    public Button myMoviesButton_id;
    public Button allMoviesButton_id;

    public void setWelcomeTextField_id(String welcomeUserText) {

        welcomeTextField_id.setText(welcomeUserText);
    }

    public void allMoviesClick(ActionEvent actionEvent) {
    }

    public void myMoviesClick(ActionEvent actionEvent) {
    }
}
