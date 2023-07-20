package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MoviesManager;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.List;

public class MoviesAdminController {

    @FXML
    public TableView<Movies> movieTable_id;

    public TextField searchButtonTextField1_id;

    private final MoviesManager moviesManager = new MoviesManager();
    public TextField release_year_id;
    public TextField ratings_id;
    public TextField language_id;
    public TextField genre_id;
    public TextField duration_id;
    public Button addButton_id;
    public TextField price_id;
    public Button deleteButton_id;
    public Button updateButton_id;
    public ImageView searchButton1_id;
    public TextField name_id;

    public void initialize() {

        searchButton1_id.setOnMouseClicked(event -> {
            searchButtonClick();
        });

        setupTableView();
        refreshTable();
    }

    /**
     * fetches all movies from the database using the moviesDao
     * updates the TableView with the fetched movie list
     */
    private void refreshTable() {
        try {
            movieTable_id.setItems(FXCollections.observableArrayList(moviesManager.getAll()));
        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates the required table columns based on the Movies table fields name
     * adds those columns to the TableView
     */
    private void setupTableView() {
        // Set cell value factory for each column

        movieTable_id.getColumns().clear();

        TableColumn<Movies, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Movies, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("movie_name"));

        TableColumn<Movies, String> genreColumn = new TableColumn<>("GENRE");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Movies, Integer> durationColumn = new TableColumn<>("DURATION");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<Movies, Double> ratingsColumn = new TableColumn<>("RATINGS");
        ratingsColumn.setCellValueFactory(new PropertyValueFactory<>("ratings"));

        TableColumn<Movies, Integer> releaseYearColumn = new TableColumn<>("RELEASE YEAR");
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("release_year"));

        TableColumn<Movies, String> languageColumn = new TableColumn<>("LANGUAGE");
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));

        TableColumn<Movies, Double> priceColumn = new TableColumn<>("PRICE");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        addColumnsToTableView(idColumn, nameColumn, genreColumn,ratingsColumn,releaseYearColumn,languageColumn,durationColumn,priceColumn);

    }

    /**
     * google it
     * indicates that the method does not perform potentially unsafe operations on the varargs parameter
     */
    @SafeVarargs
    private final void addColumnsToTableView(TableColumn<Movies, ?>... columns) {
        movieTable_id.getColumns().addAll(columns);
    }



    public void updateClick() {
    }

    public void deleteClick() {
    }

    public void addClick() {
    }

    /**
     * calls the DAO method to fetch all the users based on the search text field
     * updates the TableView with the fetched user list
     */
    public void searchButtonClick() {
        String searchText = searchButtonTextField1_id.getText();
        try {

            List<Movies> searchedMovies = moviesManager.searchByMovie_name(searchText);
            movieTable_id.setItems(FXCollections.observableArrayList(searchedMovies));
        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        }
    }
}
