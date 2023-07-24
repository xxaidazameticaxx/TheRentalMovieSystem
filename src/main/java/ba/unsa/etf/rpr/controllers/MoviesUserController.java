package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MoviesManager;
import ba.unsa.etf.rpr.business.RentsManager;
import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    private final RentsManager rentsManager = new RentsManager();
    public Button rentButton_id;

    /**
     * Initializes the MoviesUserController by setting up the TableView, ChoiceBox, and movie list.
     */
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

        movieTable_id.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                name_id.setText(newSelection.getMovie_name());
                genre_id.setText(newSelection.getGenre());
                duration_id.setText(String.valueOf(newSelection.getDuration()));
                ratings_id.setText(String.valueOf(newSelection.getRatings()));
                release_year_id.setText(String.valueOf(newSelection.getRelease_year()));
                price_id.setText(String.valueOf(newSelection.getPrice()));
                language_id.setText(newSelection.getLanguage());
            }
        });

        setupTableView();
        refreshTable();
    }

    /**
     * Sets up the required table columns based on the Movies table fields' names and adds those columns to the TableView.
     */
    private void setupTableView() {

        movieTable_id.getColumns().clear();

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

        addColumnsToTableView(nameColumn, genreColumn,ratingsColumn,releaseYearColumn,languageColumn,durationColumn,priceColumn);

    }


    /**
     * Helper method to add the provided columns to the TableView.
     *
     * @param columns The columns to be added to the TableView.
     */
    @SafeVarargs
    private final void addColumnsToTableView(TableColumn<Movies, ?>... columns) {
        movieTable_id.getColumns().addAll(columns);
    }

    /**
     * Fetches all movies from the database using the MoviesManager and updates the TableView with the fetched movie list.
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
     * Handles the button click event for the "Search" button.
     * Calls the MoviesManager to fetch all the movies based on the search text field and updates the TableView with the fetched movie list.
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

    /**
     * Handles the button click event for the "Back" button.
     * Navigates back to the user menu.
     *
     * @param mouseEvent
     * @throws IOException
     */
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


    /**
     * Handles the button click event for the "Rent" button.
     * Rents the selected movie and adds a new Rent record to the database.
     * Shows a confirmation alert upon successful renting.
     */
    public void rentClick() throws TheMovieRentalSystemException {
        Movies selectedMovie = movieTable_id.getSelectionModel().getSelectedItem();
        if(selectedMovie != null){
            Rents rent = new Rents();
            rent.setRent_date(new Date());
            rent.setUser(usersManager.getUserByUsernameAndPassword(LoginController.getUsernameField(),LoginController.getPasswordField()));
            rent.setReturn_date(null);
            rent.setMovie(selectedMovie);
            rentsManager.add(rent);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successfully rented");
            alert.setHeaderText("The movie is waiting for you in the nearest Movie Rental Shop!");
            alert.setContentText("You have two days to pick it up...");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("None selected");
            alert.setContentText("You need to select a movie to rent it!");
            alert.showAndWait();
        }
    }
}