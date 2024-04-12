package org.example.movieclient.UI.WatchList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.UI.MovieDetails.MovieDetailsController;
import org.example.movieclient.client.Client;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class WatchListScreen extends Stage {

    private final WatchListController watchListController;

    public WatchListScreen(User user, MainMovieScreenView mainMovieScreenView) {
        watchListController = new WatchListController();
        watchListController.setUser(user);
        watchListController.setMainMovieScreenView(mainMovieScreenView);

        setTitle("Watch List");

        List<Movie> watchList = user.getUserMovieWatchList();

        HBox movieListContainer = new HBox();
        movieListContainer.setPadding(new Insets(20));
        movieListContainer.setSpacing(20);

        for (Movie movie : watchList) {
            VBox movieEntry = createMovieEntry(movie);
            movieListContainer.getChildren().add(movieEntry);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(movieListContainer);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane);
        setScene(scene);
    }

    private VBox createMovieEntry(Movie movie) {
        ImageView thumbnail = new ImageView(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(movie.getMovieImage()))));
        thumbnail.setFitWidth(150);
        thumbnail.setFitHeight(200);

        VBox movieDetailsContainer = new VBox();
        movieDetailsContainer.setSpacing(10);

        Label nameLabel = new Label("Name: " + movie.getMovieName());
        Label categoryLabel = new Label("Category: " + movie.getMovieCategory().toString());
        Label timeLabel = new Label("Time: " + movie.getMovieTime());

        // Create a TextArea for the synopsis
        TextArea synopsisTextArea = new TextArea(movie.getMovieSynopsis());
        synopsisTextArea.setEditable(false);
        synopsisTextArea.setWrapText(true);
        synopsisTextArea.setPrefWidth(300);
        synopsisTextArea.setPrefHeight(100);

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeFromWatchList(movie));

        movieDetailsContainer.getChildren().addAll(thumbnail, nameLabel, categoryLabel, timeLabel, synopsisTextArea, removeButton);
        movieDetailsContainer.setPadding(new Insets(10));
        movieDetailsContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        return movieDetailsContainer;
    }

    private void removeFromWatchList(Movie movie) {
        MovieDetailsController.removeFromWatchList(movie, watchListController.getUser());
        try {
            boolean isRemoved = (boolean) Client.receiveFromServer();
            if (isRemoved) {
                DialogManager.showSuccessDialog("Success", "The Movie Removed from Watch List Successfully");
                watchListController.getMainMovieScreenView().refreshData(watchListController.getUser());
                close();
            } else {
                DialogManager.showErrorDialog("Error", "Failed to remove the movie from watch list");
            }
        } catch (IOException e1) {
            DialogManager.showErrorDialog("Error", "Failed to communicate with the server");
        }
    }
}
