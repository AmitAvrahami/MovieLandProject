package org.example.movieclient.UI.MovieUploader;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.movieclient.UI.HelperClass;
import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.model.actor.Actor;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.movie.MovieCategory;
import org.example.movieclient.model.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class MovieUploader extends Application {

    public MovieUploaderController getMovieUploaderController() {
        return movieUploaderController;
    }

    public void setMovieUploaderController(MovieUploaderController movieUploaderController) {
        this.movieUploaderController = movieUploaderController;
    }

    private MovieUploaderController movieUploaderController;

    public MovieUploader(User user) {
        movieUploaderController = new MovieUploaderController();
        movieUploaderController.setUser(user);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie Uploader");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Movie Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        Label categoryLabel = new Label("Category:");
        GridPane.setConstraints(categoryLabel, 0, 1);
        ComboBox<MovieCategory> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(MovieCategory.values());
        categoryComboBox.setValue(MovieCategory.NONE);
        GridPane.setConstraints(categoryComboBox, 1, 1);

        Label timeLabel = new Label("Movie Time:");
        GridPane.setConstraints(timeLabel, 0, 2);

        Label hoursLabel = new Label("Hours:");
        GridPane.setConstraints(hoursLabel, 1, 2);
        ComboBox<Integer> hourComboBox = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            hourComboBox.getItems().add(i);
        }
        hourComboBox.setValue(0);
        GridPane.setConstraints(hourComboBox, 1, 3);

        Label minutesLabel = new Label("Minutes:");
        GridPane.setConstraints(minutesLabel, 2, 2);
        ComboBox<Integer> minuteComboBox = new ComboBox<>();
        for (int i = 0; i <= 59; i++) {
            minuteComboBox.getItems().add(i);
        }
        minuteComboBox.setValue(0);
        GridPane.setConstraints(minuteComboBox, 2, 3);

        Label secondsLabel = new Label("Seconds:");
        GridPane.setConstraints(secondsLabel, 3, 2);
        ComboBox<Integer> secondComboBox = new ComboBox<>();
        for (int i = 0; i <= 59; i++) {
            secondComboBox.getItems().add(i);
        }
        secondComboBox.setValue(0); // Default value
        GridPane.setConstraints(secondComboBox, 3, 3);

        Label synopsisLabel = new Label("Synopsis:");
        GridPane.setConstraints(synopsisLabel, 0, 4);
        TextArea synopsisTextArea = new TextArea();
        GridPane.setConstraints(synopsisTextArea, 1, 4);


        Button addActorButton = new Button("Add Actor");
        GridPane.setConstraints(addActorButton, 1, 6);
        addActorButton.setOnAction(event -> {
            openAddActorWindow();
        });


        Button uploadImageButton = new Button("Upload Image");
        GridPane.setConstraints(uploadImageButton, 0, 6);
        uploadImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
                    String base64Image = Base64.getEncoder().encodeToString(fileBytes);
                    movieUploaderController.setImageToSave(base64Image);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });



        Button uploadButton = new Button("Upload Movie");
        GridPane.setConstraints(uploadButton, 1, 7);
        uploadButton.setOnAction(event -> {
            String movieName = nameInput.getText();
            MovieCategory movieCategory = categoryComboBox.getValue();
            int hours = hourComboBox.getValue();
            int minutes = minuteComboBox.getValue();
            int seconds = secondComboBox.getValue();
            String movieTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            String movieSynopsis = synopsisTextArea.getText();
            movieUploaderController.setNewMovieToUpload(new Movie(movieName, movieSynopsis, movieCategory, movieTime, movieUploaderController.getImageToSave(), movieUploaderController.getActorsList()));
            boolean isMovieUpload = HelperClass.UploadMovieToServer(movieUploaderController.getNewMovieToUpload());
            HelperClass.movieUploadMessage(isMovieUpload,primaryStage, movieUploaderController.getUser());

        });

        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 7);
        backButton.setOnAction(event -> {
            if (movieUploaderController.getPreviousScreen() != null) {
                try {
                    movieUploaderController.getPreviousScreen().start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, categoryLabel, categoryComboBox, timeLabel, hoursLabel, hourComboBox, minutesLabel, minuteComboBox, secondsLabel, secondComboBox, synopsisLabel, synopsisTextArea, addActorButton, uploadImageButton, uploadButton,backButton);
        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void openAddActorWindow() {
        Stage addActorStage = new Stage();
        addActorStage.setTitle("Add Actor");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Actor Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        Label lastNameLabel = new Label("Actor Last Name:");
        GridPane.setConstraints(lastNameLabel, 0, 1);
        TextField lastNameInput = new TextField();
        GridPane.setConstraints(lastNameInput, 1, 1);

        Label ageLabel = new Label("Actor Age:");
        GridPane.setConstraints(ageLabel, 0, 2);
        TextField ageInput = new TextField();
        GridPane.setConstraints(ageInput, 1, 2);

        Button addActorButton = new Button("Add Actor");
        GridPane.setConstraints(addActorButton, 1, 3);
        addActorButton.setOnAction(event -> {
            String actorName = nameInput.getText();
            String actorLastName = lastNameInput.getText();
            Integer actorAge = Integer.parseInt(ageInput.getText());
            movieUploaderController.setNewActorToUpload(new Actor(actorName, actorLastName, actorAge));
            movieUploaderController.getActorsList().add(movieUploaderController.getNewActorToUpload());
            System.out.println("Added Actor: " + actorName + " " + actorLastName + ", Age: " + actorAge);
            addActorStage.close();
        });

        grid.getChildren().addAll(nameLabel, nameInput, lastNameLabel, lastNameInput, ageLabel, ageInput, addActorButton);

        Scene scene = new Scene(grid, 300, 200);
        addActorStage.setScene(scene);
        addActorStage.show();
    }


}
