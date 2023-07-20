package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MoviesManager;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AllMoviesUserController {

    private final MoviesManager MoviesManager=new MoviesManager();
    public TextField searchButton_id;
    public TableView<Movies> allMovieTable_id;
    public TableColumn<Movies,String> column_name_id;
    public TableColumn<Movies,String> column_genre_id;
    public TableColumn<Movies, Double> column_ratings_id;
    public TableColumn<Movies,Integer> column_release_year_id;
    public TableColumn<Movies,String> column_language_id;
    public TableColumn<Movies,Integer> column_duration_id;
    public TableColumn<Movies,Double> column_price_id;

    public void initialize() {

        column_name_id.setCellValueFactory(new PropertyValueFactory<>("movie_name"));
        column_ratings_id.setCellValueFactory(new PropertyValueFactory<>("ratings"));
        column_genre_id.setCellValueFactory(new PropertyValueFactory<>("genre"));
        column_release_year_id.setCellValueFactory(new PropertyValueFactory<>("release_year"));
        column_language_id.setCellValueFactory(new PropertyValueFactory<>("language"));
        column_duration_id.setCellValueFactory(new PropertyValueFactory<>("duration"));
        column_price_id.setCellValueFactory(new PropertyValueFactory<>("price"));

        /*actionColumn.setCellFactory(new DoubleButtonCellFactory(editEvent -> {
            int quoteId = Integer.parseInt(((Button)editEvent.getSource()).getUserData().toString());
            editQuoteScene(quoteId);
        }, (deleteEvent -> {
            int quoteId = Integer.parseInt(((Button)deleteEvent.getSource()).getUserData().toString());
            deleteQuote(quoteId);
        })));
    */
        refreshMovies();
    }

    private void refreshMovies(){
        try {
            allMovieTable_id.setItems(FXCollections.observableList(MoviesManager.getAll()));
            allMovieTable_id.refresh();
        } catch (TheMovieRentalSystemException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }


    public void searchButtonClick(ActionEvent actionEvent) {
        String s = searchButton_id.getText();
    }
}
