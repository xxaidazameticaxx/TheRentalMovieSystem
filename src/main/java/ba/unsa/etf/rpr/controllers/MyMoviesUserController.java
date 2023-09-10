package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.RentsManager;
import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


public class MyMoviesUserController {

    @FXML
    public TableView<Rents> myMoviesTable_id;
    private final UsersManager usersManager = new UsersManager();
    private final RentsManager rentsManager = new RentsManager();

    /**
     * Initializes the MyMoviesUserController by setting up the TableView, checking for overdue movies, and refreshing the table.
     */
    public void initialize() {

        setupTableView();
        refreshTable();
        checkOverdueMovies();
    }

    /**
     * Checks for overdue movies and displays a warning alert if any of the user's rented movies are overdue.
     */
    private void checkOverdueMovies() {
        for (Rents rent : myMoviesTable_id.getItems()) {
            if (rent.getReturn_date() == null) {
                Date rentDate = rent.getRent_date();
                Date currentDate = new Date();
                long diffInMillis = Math.abs(currentDate.getTime() - rentDate.getTime());
                long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
                if (diffInDays > 7) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Overdue Movie");
                    alert.setHeaderText("You have an overdue movie to return!");
                    alert.setContentText("Please return the movie '" + rent.getMovie().getMovie_name() + "' to the store as soon as possible.");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Sets up the required table columns based on the Rents and Movies table fields' names and adds those columns to the TableView.
     */
    private void setupTableView() {

        myMoviesTable_id.getColumns().clear();

        TableColumn<Rents, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMovie().getMovie_name()));

        TableColumn<Rents, Double> priceColumn = new TableColumn<>("PRICE");
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMovie().getPrice()).asObject());

        TableColumn<Rents, Date> rentColumn = new TableColumn<>("RENT DATE");
        rentColumn.setCellValueFactory(new PropertyValueFactory<>("rent_date"));

        TableColumn<Rents, Date> returnColumn = new TableColumn<>("RETURN DATE");
        returnColumn.setCellValueFactory(new PropertyValueFactory<>("return_date"));

        addColumnsToTableView(nameColumn, priceColumn,rentColumn,returnColumn);

    }

    /**
     * Helper method to add the provided columns to the TableView.
     *
     * @param columns The columns to be added to the TableView.
     */
    @SafeVarargs
    private final void addColumnsToTableView(TableColumn<Rents, ?>... columns) {
        myMoviesTable_id.getColumns().addAll(columns);
    }

    /**
     * Fetches all the user's rented movies from the database using the RentsManager
     * and updates the TableView with the fetched movie list.
     */
    private void refreshTable() {
        try {
            // data changes not refreshing in some cases, so I added the clear method to force the tableview to change
            myMoviesTable_id.getItems().clear();
            myMoviesTable_id.setItems(FXCollections.observableArrayList(rentsManager.getUserIssuedMovies(usersManager.getUserByUsernameAndPassword(LoginController.getUsernameField(),UsersManager.hashPassword(LoginController.getPasswordField())))));
        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
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

}