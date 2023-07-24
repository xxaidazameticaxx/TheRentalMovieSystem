package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MoviesManager;
import ba.unsa.etf.rpr.business.RentsManager;
import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
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
import javafx.util.converter.DateStringConverter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RentsAdminController {
    public ImageView backButton_id;
    public TextField user_id;
    private final RentsManager rentsManager = new RentsManager();
    private final MoviesManager moviesManager = new MoviesManager();
    private final UsersManager usersManager = new UsersManager();
    public TextField movie_id;
    public Button deleteButton_id;
    public Button addButton_id;
    public Button helpButton_id;
    public TableView<Rents> rentTable_id;
    public DatePicker rentDate_id;

    public void initialize() {

        rentTable_id.setEditable(true);

        setupTableView();
        refreshTable();
    }

    /**
     * creates the required table columns based on the Rents table fields name
     * adds those columns to the TableView
     */
    private void setupTableView() {

        rentTable_id.getColumns().clear();

        TableColumn<Rents, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Rents, Integer> userColumn = new TableColumn<>("USER ID");
        userColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getUser().getId()).asObject());

        TableColumn<Rents, Integer> movieColumn = new TableColumn<>("MOVIE ID");
        movieColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMovie().getId()).asObject());


        TableColumn<Rents, Date> rentColumn = new TableColumn<>("RENT DATE");
        rentColumn.setCellValueFactory(new PropertyValueFactory<>("rent_date"));

        TableColumn<Rents, Date> returnColumn = new TableColumn<>("RETURN DATE");
        returnColumn.setCellValueFactory(new PropertyValueFactory<>("return_date"));

        returnColumn.setCellFactory(col -> new TextFieldTableCell<>(new DateStringConverter("yyyy-MM-dd")));

        returnColumn.setOnEditCommit(event -> {
            Rents rent = event.getTableView().getItems().get(event.getTablePosition().getRow());
            rent.setReturn_date(event.getNewValue());
            try {
                rentsManager.update(rent);
                refreshTable();
            } catch (TheMovieRentalSystemException ex) {
                throw new RuntimeException(ex);
            }
        });

        addColumnsToTableView(idColumn,rentColumn,returnColumn,userColumn,movieColumn);

    }

    /**
     * google it
     * indicates that the method does not perform potentially unsafe operations on the varargs parameter
     */
    @SafeVarargs
    private final void addColumnsToTableView(TableColumn<Rents, ?>... columns) {
        rentTable_id.getColumns().addAll(columns);
    }

    /**
     * fetches all rents from the database using the rentsDao
     * updates the TableView with the fetched rent list
     */
    private void refreshTable() {
        try {
            // data changes not refreshing in some cases, so I added the clear method to force the tableview to change
            rentTable_id.getItems().clear();
            rentTable_id.setItems(FXCollections.observableArrayList(rentsManager.getAll()));
        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        }
    }


    public void addClick() {
        try {
            Rents rent = new Rents();
            rent.setRent_date(java.sql.Date.valueOf(rentDate_id.getValue()));
            rent.setReturn_date(null);
            List<Users> listOfUsers = usersManager.getAll();
            boolean userExists=false;
            for(Users x:listOfUsers){
                if(x.getId()== Integer.parseInt(user_id.getText()))
                    userExists = true;
            }

            if(!userExists){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid data. User ID does not exist in the system!");
                alert.showAndWait();
                return;
            }


            List<Movies> listOfMovies = moviesManager.getAll();
            boolean movieExists=false;
            for(Movies x:listOfMovies){
                if(x.getId()== Integer.parseInt(movie_id.getText()))
                    movieExists = true;
            }

            if(!movieExists){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid data. Movie ID does not exist in the system!");
                alert.showAndWait();
                return;
            }


            rent.setUser(usersManager.getById(Integer.parseInt(user_id.getText())));
            rent.setMovie(moviesManager.getById(Integer.parseInt(movie_id.getText())));

            rentsManager.add(rent);

            user_id.setText("");
            movie_id.setText("");
            rentDate_id.setValue(null);

            refreshTable();

        }
        catch(TheMovieRentalSystemException e){

            e.printStackTrace();
        }
    }

    /**
     * should be used only when a mistake is made, otherwise all rents stay in the system
     */
    public void deleteClick() {
        Rents selectedRent = rentTable_id.getSelectionModel().getSelectedItem();

        if (selectedRent != null) {
            try {
                rentTable_id.getItems().remove(selectedRent);
                rentsManager.delete(selectedRent.getId());

            } catch (TheMovieRentalSystemException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("None selected");
            alert.setContentText("You need to select a rent to delete it!");
            alert.showAndWait();
        }
    }

    public void backclick(MouseEvent mouseEvent) throws IOException {
        Scene scene;
        Stage stage;
        FXMLLoader loader;

            loader = new FXMLLoader(getClass().getResource("/fxml/adminMenu.fxml"));
            Parent root = loader.load();
            AdminMenuController aMC = loader.getController();
            aMC.setWelcomeTextField1_id("Welcome " + LoginController.getUsernameField() + ", please select: ");
            stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void helpClick() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/helpRentsAdmin.fxml")));
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
