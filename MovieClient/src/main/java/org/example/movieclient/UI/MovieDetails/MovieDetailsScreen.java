package org.example.movieclient.UI.MovieDetails;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.UI.HelperClass;
import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.client.Client;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.PermissionLevel;
import org.example.movieclient.model.user.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;


public class MovieDetailsScreen extends Stage {

    public MovieDetailsScreen(Movie movie, User user, MainMovieScreenView mainMovieScreenView) {
        setTitle("Movie Details");

        // Create UI components
        String base64Image = movie.getMovieImage();
        byte[] imageData = Base64.getDecoder().decode(base64Image);
        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imageData)));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Label nameLabel = new Label("Name:");
        Label nameValueLabel = new Label(movie.getMovieName());

        Label categoryLabel = new Label("Category:");
        Label categoryValueLabel = new Label(movie.getMovieCategory().toString());

        Label timeLabel = new Label("Time:");
        Label timeValueLabel = new Label(movie.getMovieTime());

        Label synopsisLabel = new Label("Synopsis:");
        TextArea synopsisTextArea = new TextArea(movie.getMovieSynopsis());
        synopsisTextArea.setEditable(false);
        synopsisTextArea.setWrapText(true);
        synopsisTextArea.setPrefWidth(300);
        synopsisTextArea.setPrefHeight(100);

        Button addToWishListButton = new Button("Add to Wish List");
        addToWishListButton.setOnAction(e -> {
            MovieDetailsController.addToWishList(movie, user);
            boolean isAdd = false;
            try {
                isAdd = (boolean) Client.receiveFromServer();
                if (isAdd) {
                    DialogManager.showSuccessDialog("Success", "The Movie Upload To Watch List");
                    close();
                    mainMovieScreenView.refreshData(user);
                    mainMovieScreenView.start(new Stage());
                } else {
                    DialogManager.showErrorDialog("Some Thing Wrong", "The Movie already exists in your watch list");
                }
            } catch (IOException e1) {
                DialogManager.showErrorDialog("Some Thing Wrong", "The Movie doesn't upload");
            }
        });

        Button removeMovieButton = new Button("Remove Movie");
        removeMovieButton.setOnAction(e -> {
            if (user.getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                MovieDetailsController.removeMovie(movie, user);
                try {
                    boolean isRemoved = (boolean) Client.receiveFromServer();
                    if (isRemoved) {
                        DialogManager.showSuccessDialog("Success", "The Movie Removed Successfully");
                        mainMovieScreenView.refreshData(user);
                        mainMovieScreenView.start(new Stage());
                        close();
                    } else {
                        DialogManager.showErrorDialog("Error", "Failed to remove the movie");
                    }
                } catch (IOException e1) {
                    DialogManager.showErrorDialog("Error", "Failed to communicate with the server");
                }
            } else
                DialogManager.showDeleteFailDialog("Permission Denied", "You do not have permission to perform this action.");
        });


        Button removeFromWatchListButton = new Button("Remove from Watch List");
        removeFromWatchListButton.setOnAction(e -> {
            MovieDetailsController.removeFromWatchList(movie, user);
            try {
                boolean isRemoved = (boolean) Client.receiveFromServer();
                if (isRemoved) {
                    DialogManager.showSuccessDialog("Success", "The Movie Removed from Watch List Successfully");
                    close();
                    mainMovieScreenView.refreshData(user);
                    mainMovieScreenView.start(new Stage());
                } else {
                    DialogManager.showErrorDialog("Error", "Failed to remove the movie from watch list");
                }
            } catch (IOException e1) {
                DialogManager.showErrorDialog("Error", "Failed to communicate with the server");
            }
        });

        Button backPervScreen = new Button("Back");
        backPervScreen.setOnAction(e -> {
            close();
            mainMovieScreenView.start(new Stage());
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(imageView, 0, 0, 2, 1);
        gridPane.addRow(1, nameLabel, nameValueLabel);
        gridPane.addRow(2, categoryLabel, categoryValueLabel);
        gridPane.addRow(3, timeLabel, timeValueLabel);
        gridPane.addRow(4, synopsisLabel, synopsisTextArea);
        gridPane.add(addToWishListButton, 0, 5);
        gridPane.add(removeMovieButton, 1, 5);
        gridPane.add(removeFromWatchListButton, 2, 5);
        gridPane.add(backPervScreen, 3, 5);

        Scene scene = new Scene(gridPane);
        setScene(scene);
    }


}
