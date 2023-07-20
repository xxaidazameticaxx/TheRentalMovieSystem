package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.dao.UsersDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;


public class UsersAdminController {
    public TextField username_id;
    public TextField password_id;
    public TextField admin_id;
    public Button addButton_id;
    public Button deleteButton_id;
    public Button updateButton_id;

    public List<Users>userList;

    @FXML
    public TableView<Users> userTable_id;

    private final UsersManager usersManager = new UsersManager();
    public TextField searchButtonTextField_id;
    public ImageView searchButtonImage_id;

    public void initialize() {

        searchButtonImage_id.setOnMouseClicked(event -> {
            searchButtonClick();
        });

        setupTableView();
        refreshTable();
    }

    /**
     * creates the required table columns based on the Users table fields name
     * adds those columns to the TableView
     */
    private void setupTableView() {
        // Set cell value factory for each column

        userTable_id.getColumns().clear();

        TableColumn<Users, String> usernameColumn = new TableColumn<>("USERNAME");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Users, String> passwordColumn = new TableColumn<>("PASSWORD");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Users, Boolean> adminColumn = new TableColumn<>("ADMIN");
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

        addColumnsToTableView(usernameColumn, passwordColumn, adminColumn);

    }

    /**
     * google it
     * indicates that the method does not perform potentially unsafe operations on the varargs parameter
     */
    @SafeVarargs
    private final void addColumnsToTableView(TableColumn<Users, ?>... columns) {
        userTable_id.getColumns().addAll(columns);
    }

    /**
     * fetches all users from the database using the usersDao
     * updates the TableView with the fetched user list
     */
    private void refreshTable() {
        try {
            userTable_id.setItems(FXCollections.observableArrayList(usersManager.getAll()));
        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls the DAO method to fetch all the users based on the search text field
     * updates the TableView with the fetched user list
     */
    public void searchButtonClick() {
        String searchText = searchButtonTextField_id.getText();
        try {

            List<Users> searchedUsers = usersManager.getUsersByUsername(searchText);
            userTable_id.setItems(FXCollections.observableArrayList(searchedUsers));
        } catch (TheMovieRentalSystemException e) {
            e.printStackTrace();
        }
    }

    /**
     * treba dodati pop up za validaciju unesenih podataka
     */

    public void addClick() {

        try {
            userList = usersManager.getAll();
            for(Users x:userList){
                if(x.getUsername().equals(username_id.getText())) throw new TheMovieRentalSystemException("A user with this username already exists.");
            }

            if(password_id.getText().length()<6) throw new TheMovieRentalSystemException("Password");
            if(username_id.getText().length()<6) throw new TheMovieRentalSystemException("Username");

            Users user = new Users();
            user.setUsername(username_id.getText());
            user.setAdmin(Boolean.parseBoolean(admin_id.getText()));
            user.setPassword(password_id.getText());
            usersManager.add(user);
            password_id.setText("");
            admin_id.setText("");
            username_id.setText("");
            refreshTable();

        }catch(TheMovieRentalSystemException e){
            if(Objects.equals(e.getMessage(), "A user with this username already exists.")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User already exists");
                alert.setContentText("The username and password you entered matches an existing user.");
                alert.showAndWait();
            }
            else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage()+" too short!");
            alert.setContentText(e.getMessage()+" must contain at least 6 characters.");
            alert.showAndWait();
            }
        }
    }




    public void deleteClick(ActionEvent actionEvent) {
        try {
            userList = usersManager.getAll();
            for(Users x:userList){
                if(x.getUsername().equals(username_id.getText()) && x.getPassword().equals(password_id.getText())) {
                    usersManager.delete(x.getId());
                    password_id.setText("");
                    admin_id.setText("");
                    username_id.setText("");
                    refreshTable();
                    return;
                }
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User does not exist");
            alert.setContentText("The username and password you entered doesn't match any existing user.");
            alert.showAndWait();


        }
        catch(TheMovieRentalSystemException e) {
            e.printStackTrace();
        }
    }

    public void updateClick(ActionEvent actionEvent) {
    }
}
