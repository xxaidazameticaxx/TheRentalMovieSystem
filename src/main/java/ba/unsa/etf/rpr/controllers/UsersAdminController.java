package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UsersManager;
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
import javafx.util.converter.BooleanStringConverter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class UsersAdminController {

    @FXML
    public TableView<Users> userTable_id;
    public TextField username_id;
    public TextField password_id;
    public TextField admin_id;
    public Button addButton_id;
    public Button deleteButton_id;
    public Button updateButton_id;
    public List<Users>userList;
    public TextField searchButtonTextField_id;
    public ImageView searchButtonImage_id;
    public ImageView backButton_id;
    private final UsersManager usersManager = new UsersManager();
    public Button helpButton1_id;

    public void initialize() {

        userTable_id.setEditable(true);

        searchButtonImage_id.setOnMouseClicked(event -> searchButtonClick());

        // this displays all the users when the search text field is empty
        searchButtonTextField_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                refreshTable();
            }
        });

        setupTableView();
        refreshTable();
    }


    /**
     * creates the required table columns based on the Users table fields name
     * adds those columns to the TableView
     */
    private void setupTableView() {

        userTable_id.getColumns().clear();

        TableColumn<Users, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Users, String> usernameColumn = new TableColumn<>("USERNAME");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Users, String> passwordColumn = new TableColumn<>("PASSWORD");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Users, Boolean> adminColumn = new TableColumn<>("ADMIN");
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

        //allows the admin column to be changed after double-clicking it to open edit field
        adminColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        adminColumn.setOnEditCommit(event -> {
            Users user = event.getTableView().getItems().get(event.getTablePosition().getRow());
            user.setAdmin(event.getNewValue());
            try {
                usersManager.update(user);
                refreshTable();
            } catch (TheMovieRentalSystemException ex) {
                throw new RuntimeException(ex);
            }
        });

        addColumnsToTableView(idColumn,usernameColumn, passwordColumn, adminColumn);

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
            // data changes not refreshing in some cases, so I added the clear method to force the tableview to change
            userTable_id.getItems().clear();
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
     * adds a new user based on the input fields (same as sign up)
     */

    public void addClick() {

        try {
            userList = usersManager.getAll();

            for(Users x:userList){
                if(x.getUsername().equals(username_id.getText())) throw new TheMovieRentalSystemException("A user with this username already exists.");
            }

            if(password_id.getText().length()<6)
                throw new TheMovieRentalSystemException("Password");
            if(username_id.getText().length()<6)
                throw new TheMovieRentalSystemException("Username");

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




    /**
     * fetches the mouse selected user from the TableView and removes it from the database
     */
    public void deleteClick() {

        Users selectedUser = userTable_id.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                userTable_id.getItems().remove(selectedUser);
                usersManager.delete(selectedUser.getId());

            } catch (TheMovieRentalSystemException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("None selected");
            alert.setContentText("You need to select a user to delete it!");
            alert.showAndWait();
        }
    }

    /**
     * changes the admin status of other users
     * password and username can not be changed
     */
    public void updateClick() {
        try {
            userList = usersManager.getAll();
            for(Users x:userList){
                if(x.getPassword().equals(password_id.getText()) && x.getUsername().equals(username_id.getText())) {
                    x.setAdmin(Boolean.parseBoolean(admin_id.getText()));
                    usersManager.update(x);
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

    /**
     * same method as in LoginController
     * possible refactor
     */
    public void backclick(MouseEvent mouseEvent) throws IOException {
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

        Stage stage;
        Scene scene;
        FXMLLoader loader;

        if(user.isAdmin()) {
            loader = new FXMLLoader(getClass().getResource("/fxml/adminMenu.fxml"));
            Parent root = loader.load();
            AdminMenuController aMC = loader.getController();
            aMC.setWelcomeTextField1_id("Welcome " + LoginController.getUsernameField() + ", please select: ");
            stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
        }
        else{
            loader = new FXMLLoader(getClass().getResource("/fxml/userMenu.fxml"));
            Parent root = loader.load();
            UserMenuController uMC = loader.getController();
            uMC.setWelcomeTextField_id("Welcome " + LoginController.getUsernameField() + ", please select: ");
            stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
        }
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void helpClick1() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/helpUsersAdmin.fxml")));
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
