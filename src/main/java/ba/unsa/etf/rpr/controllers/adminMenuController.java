package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class adminMenuController {
    public TextField welcomeTextField1_id;
    public Button viewAllMoviesButton_id;
    public Button viewRentedMoviesButton_id;
    public Button viewAllUsersButton_id;
    public Button addMovieButton_id;
    public Button rentAMovieButton_id;
    public Button returnMovieButton_id;

    public void setWelcomeTextField1_id(String welcomeAdminText) {

        welcomeTextField1_id.setText(welcomeAdminText);
    }

    public void clickReturnMovie(ActionEvent actionEvent) {
    }

    public void clickAddMovies(ActionEvent actionEvent) {
    }

    public void clickRentAMovie(ActionEvent actionEvent) {
    }

    public void clickViewAllUsers(ActionEvent actionEvent) {
    }

    public void clickViewAllRentedMovies(ActionEvent actionEvent) {
    }

    public void clickViewAllMovies(ActionEvent actionEvent) {
    }
}
