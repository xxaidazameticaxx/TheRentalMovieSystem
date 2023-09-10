package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MoviesManager;
import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.dao.RentsDaoSQLImpl;
import ba.unsa.etf.rpr.dao.UsersDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class RecommendedMoviesController {
    /*

   @FXML
    public TableView<Object[]> movieTable_id;

     */

    @FXML
    public TableView<Movies> recommendedMovies_id;

    private final MoviesManager moviesManager = new MoviesManager();
    public void initialize() throws NoSuchAlgorithmException, TheMovieRentalSystemException {
        setupTableView();
        refreshTable();
    }

    @SafeVarargs
    private final void addColumnsToTableView(TableColumn<Movies, ?>... columns) {
        recommendedMovies_id.getColumns().addAll(columns);
    }

    /**
     * Fetches all movies from the database using the MoviesManager and updates the TableView with the fetched movie list.
     */
    private void refreshTable() {
        try {
            recommendedMovies_id.getItems().clear();

            UsersDaoSQLImpl userDao = new UsersDaoSQLImpl();

            List<Movies> recommendedMovies = moviesManager.getRecommendedMovies(userDao.getUserByUsernameAndPassword(LoginController.getUsernameField(), UsersManager.hashPassword(LoginController.getPasswordField())));
            List<Movies> randomMovies = moviesManager.getRandomMovies(userDao.getUserByUsernameAndPassword(LoginController.getUsernameField(), UsersManager.hashPassword(LoginController.getPasswordField())));


            if (recommendedMovies.size() < 3) {
                for (Movies randomMovie : randomMovies) {
                    if (!recommendedMovies.contains(randomMovie)) {
                        recommendedMovies.add(randomMovie);
                        if (recommendedMovies.size() == 3) break;
                    }
                }
            }

            recommendedMovies_id.setItems(FXCollections.observableArrayList(recommendedMovies));

        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets up the required table columns based on the Movies table fields' names and adds those columns to the TableView.
     */
    private void setupTableView() throws NoSuchAlgorithmException, TheMovieRentalSystemException {

        recommendedMovies_id.getColumns().clear();

        UsersDaoSQLImpl userDao = new UsersDaoSQLImpl();
        TableColumn<Movies, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("movie_name"));

        TableColumn<Movies, String> genreColumn = new TableColumn<>("GENRE");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Movies, Integer> durationColumn = new TableColumn<>("DURATION");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<Movies, Integer> releaseYearColumn = new TableColumn<>("RELEASE YEAR");
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("release_year"));

        TableColumn<Movies, String> languageColumn = new TableColumn<>("LANGUAGE");
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));

        TableColumn<Movies, Double> priceColumn = new TableColumn<>("PRICE");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Movies, Double> ratingsColumn = new TableColumn<>("RATINGS");
        ratingsColumn.setCellValueFactory(new PropertyValueFactory<>("ratings"));

        recommendedMovies_id.getColumns().addAll(nameColumn, genreColumn, ratingsColumn, releaseYearColumn, languageColumn, durationColumn, priceColumn);


        /*

        try {
            RentsDaoSQLImpl rentDao = new RentsDaoSQLImpl();
            UsersDaoSQLImpl userDao = new UsersDaoSQLImpl();
            List<Object[]> userAndMovieData = rentDao.getRentsIssuedByOthers(userDao.getUserByUsernameAndPassword(LoginController.getUsernameField(), UsersManager.hashPassword(LoginController.getPasswordField())));

            // Create the columns for the TableView
            TableColumn<Object[], Integer> userColumn = new TableColumn<>("User");
            TableColumn<Object[], Integer> movieColumn = new TableColumn<>("Movie");

            // Configure the cell value factories to extract data from the Object[] items
            userColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue()[0]).asObject());
            movieColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue()[1]).asObject());

            // Create the ObservableList of Object[] items
            ObservableList<Object[]> observableList = FXCollections.observableArrayList(userAndMovieData);

            // Set the columns in the TableView
            recommendedMovies_id.getColumns().addAll(userColumn, movieColumn);

            // Set the data in the TableView
            recommendedMovies_id.setItems(observableList);

        } catch (TheMovieRentalSystemException e) {
            // Handle the exception
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

         */


    }


}


