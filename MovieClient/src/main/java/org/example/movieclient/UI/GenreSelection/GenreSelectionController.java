package org.example.movieclient.UI.GenreSelection;

import com.google.gson.reflect.TypeToken;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.movie.MovieCategory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.example.movieclient.UI.DialogManager.showErrorDialog;

public class GenreSelectionController {

    public static void makeSearchForRandomMovie(MovieCategory selectedGenre) {
        if (selectedGenre != null) {
            String action = "choose random movie";
            Map<String, Object> body = new HashMap<>();
            body.put("Category", selectedGenre);
            try {
                Client.saveToServer(new Request(action, body));
            } catch (IOException ex) {
                DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");

            }
            try {
                Movie chosenMovie = Client.receiveUserOrMovieFromSever(new TypeToken<>() {});
                if (chosenMovie!=null) displayMovieDetails(chosenMovie);
                else showErrorDialog("No Movie Found", "no found movies from this category.");

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            showErrorDialog("No Genre Selected", "Please select a genre before searching.");
        }
    }

    private static void displayMovieDetails(Movie item) {
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
