package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MoviesManager;
import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MoviesUserController {

    @FXML
    public TableView<Movies> movieTable_id;
    public TextField release_year_id;
    public TextField ratings_id;
    public TextField language_id;
    public TextField genre_id;
    public TextField duration_id;
    public TextField price_id;
    public TextField name_id;
    public ImageView backButtonMovies_id;
    public ImageView searchButton1_id;
    public TextField searchButtonTextField1_id;
    public ChoiceBox<String> movieGenreChoiceBox_id;
    private final MoviesManager moviesManager = new MoviesManager();
    private final UsersManager usersManager = new UsersManager();
    public Button helpButton_id;

    public void initialize() {

        movieTable_id.setEditable(true);

        searchButton1_id.setOnMouseClicked(event -> searchButtonClick());

        // this displays all the movies when the search text field is empty
        searchButtonTextField1_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                refreshTable();
            }
        });

        movieGenreChoiceBox_id.getItems().addAll("All genres", "Action", "Adventure","Comedy","Romance","Horror","Drama","Animation","Documentary");

        movieGenreChoiceBox_id.setValue("All genres");

        movieGenreChoiceBox_id.setOnAction(e -> {
            String selectedGenre = movieGenreChoiceBox_id.getValue();
            if(selectedGenre.equals("All genres")){
                refreshTable();
            }
            else{
                try {
                    movieTable_id.setItems(FXCollections.observableArrayList(moviesManager.filterByGenre(selectedGenre)));
                } catch (TheMovieRentalSystemException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setupTableView();
        refreshTable();
    }

    /**
     * creates the required table columns based on the Movies table fields name
     * adds those columns to the TableView
     */
    private void setupTableView() {

        movieTable_id.getColumns().clear();

        TableColumn<Movies, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

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

        //allows the price column to be changed after double-clicking it to open edit field
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumn.setOnEditCommit(event -> {
            Movies movie = event.getTableView().getItems().get(event.getTablePosition().getRow());
            movie.setPrice(event.getNewValue());
            try {
                moviesManager.update(movie);
                refreshTable();
            } catch (TheMovieRentalSystemException ex) {
                throw new RuntimeException(ex);
            }
        });

        TableColumn<Movies, Double> ratingsColumn = new TableColumn<>("RATINGS");
        ratingsColumn.setCellValueFactory(new PropertyValueFactory<>("ratings"));

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

    /**
     * fetches all movies from the database using the moviesDao
     * updates the TableView with the fetched movie list
     */
    private void refreshTable() {
        try {
            // data changes not refreshing in some cases, so I added the clear method to force the tableview to change
            movieTable_id.getItems().clear();
            movieTable_id.setItems(FXCollections.observableArrayList(moviesManager.getAll()));
        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        }
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

    public void backClick(MouseEvent mouseEvent) throws IOException {
        Scene scene;
        Stage stage;
        FXMLLoader loader;

            loader = new FXMLLoader(getClass().getResource("/fxml/userMenu.fxml"));
            Parent root = loader.load();
            UserMenuController uMC = loader.getController();
            uMC.setWelcomeTextField_id("Welcome " + LoginController.getUsernameField() + ", please select: ");
            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void helpClick() {
        try {
            // TODO: 23.7.2023. change to helpmoviesuser
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/helpMoviesAdmin.fxml")));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/questionIcon.png") );
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
