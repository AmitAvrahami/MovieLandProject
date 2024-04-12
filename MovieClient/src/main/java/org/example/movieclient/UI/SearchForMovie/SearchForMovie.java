package org.example.movieclient.UI.SearchForMovie;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.UI.HelperClass;
import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.movie.MovieCategory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchForMovie extends Application {
    private MainMovieScreenView previousScreen;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Search for Movies");

        // Create UI components
        Label searchLabel = new Label("Search by:");
        ChoiceBox<String> searchCriteriaChoiceBox = new ChoiceBox<>();
        searchCriteriaChoiceBox.getItems().addAll("Movie Name", "Actor Name", "Category");
        searchCriteriaChoiceBox.setValue("Movie Name");

        TextField searchField = new TextField();
        ComboBox<MovieCategory> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(MovieCategory.values());
        categoryComboBox.setValue(MovieCategory.NONE);
        categoryComboBox.setVisible(false);

        Button searchButton = new Button("Search");
        ListView<Movie> movieListView = new ListView<>();

        // Set action for the search button
        searchButton.setOnAction(event -> {
            String searchCriteria = searchCriteriaChoiceBox.getValue();
            if ("Category".equals(searchCriteria)) {
                searchByCategory(categoryComboBox.getValue(), movieListView);
            } else {
                String searchTerm = searchField.getText();
                if ("Movie Name".equals(searchCriteria)) {
                    searchByMovieName(searchTerm, movieListView);
                } else if ("Actor Name".equals(searchCriteria)) {
                    searchByActorName(searchTerm, movieListView);
                }
            }
        });

        // Listener to toggle search components based on selected criteria
        searchCriteriaChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Category".equals(newValue)) {
                searchField.setVisible(false);
                categoryComboBox.setVisible(true);
            } else {
                searchField.setVisible(true);
                categoryComboBox.setVisible(false);
            }
        });

        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        GridPane.setConstraints(searchLabel, 0, 0);
        GridPane.setConstraints(searchCriteriaChoiceBox, 1, 0);
        GridPane.setConstraints(searchField, 0, 1);
        GridPane.setConstraints(categoryComboBox, 0, 1);
        GridPane.setConstraints(searchButton, 1, 1);
        GridPane.setConstraints(movieListView, 0, 2, 2, 1);

        gridPane.getChildren().addAll(searchLabel, searchCriteriaChoiceBox, searchField, categoryComboBox, searchButton, movieListView);

        movieListView.setCellFactory(param -> new MovieListCell());
        gridPane.setStyle("-fx-background-color: lightblue;");

        // Set scene
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 3);
        backButton.setOnAction(event -> {
            if (previousScreen != null) {
                try {
                    previousScreen.start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        gridPane.getChildren().add(backButton);
    }

    private void searchByMovieName(String searchTerm, ListView<Movie> movieListView) {
        Map<String, Object> body = new HashMap<>();
        body.put("Movie Name", searchTerm);
        try {
            Client.saveToServer(new Request("search by movie name", body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");
            System.exit(0);
        }
        updateMovieListView(movieListView);
    }

    private void searchByActorName(String searchTerm, ListView<Movie> movieListView) {
        Map<String, Object> body = new HashMap<>();
        body.put("Actor Name", searchTerm);
        try {
            Client.saveToServer(new Request("search movies by actor", body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");
            System.exit(0);
        }
        updateMovieListView(movieListView);
    }

    private void searchByCategory(MovieCategory category, ListView<Movie> movieListView) {
        Map<String, Object> body = new HashMap<>();
        body.put("Category", category);
        try {
            Client.saveToServer(new Request("search movies by category", body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");
            System.exit(0);
        }
        updateMovieListView(movieListView);
    }

    private void updateMovieListView(ListView<Movie> movieListView) {
        List<Movie> movieItems;
        try {
            movieItems = Client.receiveListMovieFromServer();
            if (movieItems.isEmpty()) {
                // Display a message indicating no movies found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Movies Found");
                alert.setHeaderText(null);
                alert.setContentText("No movies found matching the search criteria.");
                alert.showAndWait();
            } else {
                movieListView.getItems().setAll(movieItems);
            }
        } catch (IOException e) {
            // Display a message indicating an error occurred while fetching movies
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while fetching movies from the server.");
            alert.showAndWait();
        }
    }


    // Custom cell factory for the ListView
    class MovieListCell extends javafx.scene.control.ListCell<Movie> {
        @Override
        protected void updateItem(Movie item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                ImageView imageView= HelperClass.getBase64Image(item);
                setAlignment(Pos.CENTER);
                setGraphic(imageView);
                setText(item.getMovieName());
            }
        }

        public MovieListCell() {
            setOnMouseClicked(event -> {
                Movie item = getItem();
                if (item != null) {
                    displayMovieDetails(item);
                }
            });
        }



         public  void displayMovieDetails(Movie item) {
            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Movie Details");

            // Create UI components
            String base64Image = item.getMovieImage();
            byte[] imageData = Base64.getDecoder().decode(base64Image);
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageData)));
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

            Label nameLabel = new Label("Name:");
            Label nameValueLabel = new Label(item.getMovieName());

            Label categoryLabel = new Label("Category:");
            Label categoryValueLabel = new Label(item.getMovieCategory().toString());

            Label timeLabel = new Label("Time:");
            Label timeValueLabel = new Label(item.getMovieTime());

            Label synopsisLabel = new Label("Synopsis:");
            TextArea synopsisTextArea = new TextArea(item.getMovieSynopsis());
            synopsisTextArea.setEditable(false);
            synopsisTextArea.setWrapText(true);
            synopsisTextArea.setPrefWidth(300);
            synopsisTextArea.setPrefHeight(100);

            // Create layout
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(20));
            gridPane.setVgap(10);
            gridPane.setHgap(10);

            gridPane.add(imageView, 0, 0, 2, 1);
            gridPane.addRow(1, nameLabel, nameValueLabel);
            gridPane.addRow(2, categoryLabel, categoryValueLabel);
            gridPane.addRow(3, timeLabel, timeValueLabel);
            gridPane.addRow(4, synopsisLabel, synopsisTextArea);

            // Set scene
            Scene scene = new Scene(gridPane);
            detailsStage.setScene(scene);
            detailsStage.show();
        }
    }
    public MainMovieScreenView getPreviousScreen() {
        return previousScreen;
    }

    public void setPreviousScreen(MainMovieScreenView previousScreen) {
        this.previousScreen = previousScreen;
    }
}
