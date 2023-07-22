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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class MoviesAdminController {

    @FXML
    public TableView<Movies> movieTable_id;
    public TextField release_year_id;
    public TextField ratings_id;
    public TextField language_id;
    public TextField genre_id;
    public TextField duration_id;
    public Button addButton_id;
    public TextField price_id;
    public Button deleteButton_id;
    public Button updateButton_id;
    public TextField name_id;
    public ImageView backButtonMovies_id;
    public ImageView searchButton1_id;
    public TextField searchButtonTextField1_id;
    public ChoiceBox<String> movieGenreChoiceBox_id;
    private final MoviesManager moviesManager = new MoviesManager();
    private final UsersManager usersManager = new UsersManager();

    public void initialize() {

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

    /**
     * this method gets the selected movie from the TableView and removes it from the database
     */
    public void deleteClick() {

        Movies selectedMovie = movieTable_id.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            try {
                movieTable_id.getItems().remove(selectedMovie);
                moviesManager.delete(selectedMovie.getId());

            } catch (TheMovieRentalSystemException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("None selected");
            alert.setContentText("You need to select a movie to delete it!");
            alert.showAndWait();
        }
    }

    /**
     * this method should be refactored, so we can verify the input
     */
    public void addClick() {
        try {
            //REFACTOR!!!
            Movies movie = new Movies();
            movie.setMovie_name(name_id.getText());
            movie.setDuration(Integer.parseInt(duration_id.getText()));
            movie.setGenre(genre_id.getText());
            movie.setRatings(Double.parseDouble(ratings_id.getText()));
            movie.setPrice(Double.parseDouble(price_id.getText()));
            movie.setLanguage(language_id.getText());
            movie.setRelease_year(Integer.parseInt(release_year_id.getText()));

            moviesManager.add(movie);

            name_id.setText("");
            genre_id.setText("");
            duration_id.setText("");
            release_year_id.setText("");
            language_id.setText("");
            price_id.setText("");
            ratings_id.setText("");

            refreshTable();

        }catch(TheMovieRentalSystemException e){
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
        Users user;
        try{
            user = usersManager.getUserByUsernameAndPassword(LoginController.getUsernameField(),LoginController.getPasswordField());
        } catch(TheMovieRentalSystemException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User does not exist");
            alert.setContentText("The username and password you entered do not match any existing users.");
            alert.showAndWait();
            return;
        }
        Scene scene;
        Stage stage;
        FXMLLoader loader;
        if(user.isAdmin()) {
            loader = new FXMLLoader(getClass().getResource("/fxml/adminMenu.fxml"));
            Parent root = loader.load();
            AdminMenuController aMC = loader.getController();
            aMC.setWelcomeTextField1_id("Welcome " + LoginController.getUsernameField() + ", please select: ");
            stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
        }
        else {
            loader = new FXMLLoader(getClass().getResource("/fxml/userMenu.fxml"));
            Parent root = loader.load();
            UserMenuController uMC = loader.getController();
            uMC.setWelcomeTextField_id("Welcome " + LoginController.getUsernameField() + ", please select: ");
            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
        }
        stage.setScene(scene);
        stage.show();


    }
}
