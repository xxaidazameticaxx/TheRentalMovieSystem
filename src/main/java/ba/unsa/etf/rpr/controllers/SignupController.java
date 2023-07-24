package ba.unsa.etf.rpr.controllers;
import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ba.unsa.etf.rpr.dao.UsersDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Users;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;


public class SignupController {

    public Button signupButton1_id;
    public PasswordField passwordField1_id;
    public TextField usernameField1_id;
    private final UsersManager usersManager = new UsersManager();


    @FXML
    public void initialize() {
        usernameField1_id.getStyleClass().add("fieldCorrect");
        usernameField1_id.getStyleClass().add("fieldNotCorrect");
        passwordField1_id.getStyleClass().add("fieldCorrect");
        passwordField1_id.getStyleClass().add("fieldNotCorrect");

        usernameField1_id.textProperty().addListener((observableValue, o, n) -> {
            if (usernameField1_id.getText().trim().isEmpty() || usernameField1_id.getText().contains(" ")) {
                usernameField1_id.getStyleClass().removeAll("fieldCorrect");
                usernameField1_id.getStyleClass().add("fieldNotCorrect");
            } else {
                usernameField1_id.getStyleClass().removeAll("fieldNotCorrect");
                usernameField1_id.getStyleClass().add("fieldCorrect");
            }
        });

        passwordField1_id.textProperty().addListener((observableValue, o, n) -> {
            if (passwordField1_id.getText().trim().isEmpty() || passwordField1_id.getText().contains(" ") ) {
                passwordField1_id.getStyleClass().removeAll("fieldCorrect");
                passwordField1_id.getStyleClass().add("fieldNotCorrect");
            } else {
                passwordField1_id.getStyleClass().removeAll("fieldNotCorrect");
                passwordField1_id.getStyleClass().add("fieldCorrect");
            }
        });
    }

    /**
     * can be improved
     */
    public void signupclick1(ActionEvent actionEvent) throws IOException {
        try {
            UsersDaoSQLImpl user = new UsersDaoSQLImpl();
            List<Users> listOfUsers = user.getAll();
            for(Users x:listOfUsers){
                if(x.getUsername().equals(usernameField1_id.getText())) throw new TheMovieRentalSystemException("A user with this username already exists.");
            }

        } catch (TheMovieRentalSystemException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User already exists");
            alert.setContentText("The username and password you entered matches an existing user.");
            alert.showAndWait();
            return;
        }

        try{
            if(passwordField1_id.getText().length()<6) throw new TheMovieRentalSystemException("Password");
            if(usernameField1_id.getText().length()<6) throw new TheMovieRentalSystemException("Username");

            Users user = new Users();
            user.setUsername(usernameField1_id.getText());
            user.setAdmin(false);
            user.setPassword(passwordField1_id.getText());
            usersManager.add(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch(TheMovieRentalSystemException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage()+" too short!");
            alert.setContentText(e.getMessage()+" must contain at least 6 characters.");
            alert.showAndWait();
        }
    }
}
