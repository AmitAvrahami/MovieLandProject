package org.example.movieclient.UI.GenreSelection;

import com.google.gson.reflect.TypeToken;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.movie.MovieCategory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.example.movieclient.UI.DialogManager.showErrorDialog;
import static org.example.movieclient.UI.GenreSelection.GenreSelectionController.makeSearchForRandomMovie;

public class GenreSelectionScreen extends Stage {

    public GenreSelectionScreen() {
        setTitle("Genre Selection");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        ComboBox<MovieCategory> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll(MovieCategory.values());
        genreComboBox.setValue(MovieCategory.NONE);// Add your movie genres here


        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            MovieCategory selectedGenre = genreComboBox.getValue();
            makeSearchForRandomMovie(selectedGenre);
        });

        root.getChildren().addAll(genreComboBox, searchButton);

        Scene scene = new Scene(root, 300, 200);
        setScene(scene);
    }




    // Add methods to get movies by genre, select a random movie, and display the selected movie
}
