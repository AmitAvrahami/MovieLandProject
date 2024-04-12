package org.example.movieclient.UI.MainMovie;

import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.movieclient.UI.*;
import org.example.movieclient.UI.GenreSelection.GenreSelectionScreen;
import org.example.movieclient.UI.Login.LoginScreenView;
import org.example.movieclient.UI.MovieDetails.MovieDetailsScreen;
import org.example.movieclient.UI.MovieUploader.MovieUploader;
import org.example.movieclient.UI.SearchForMovie.SearchForMovie;
import org.example.movieclient.UI.WatchList.WatchListScreen;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.PermissionLevel;
import org.example.movieclient.model.user.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.movieclient.UI.DialogManager.showDeleteFailDialog;
import static org.example.movieclient.UI.MainMovie.MainMovieController.deleteAction;

public class MainMovieScreenView extends Application implements DataRefreshListener {

    private HBox movieList;
    private final MainMovieController mainMovieController = new MainMovieController();

    public MainMovieScreenView(User user) {

        mainMovieController.setUser(user);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Movie Screen");

        try {
            Client.saveToServer(new Request("get all movies"));
            mainMovieController.setAllMovies(Client.receiveListFromServer(new TypeToken<List<Movie>>() {
            }));
        } catch (IOException e) {
            showDeleteFailDialog("something Wrong", "Something Wrong");
        }

        // Creating layout for the movie list
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        HBox movieList = new HBox(20);
        movieList.setPadding(new Insets(20));
        movieList.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        if (mainMovieController.getAllMovies() != null) {
            for (Movie movie : mainMovieController.getAllMovies()) {
                VBox movieItem = new VBox(10); // Vertical box to hold each movie item
                movieItem.setPadding(new Insets(10));
                ImageView imageView = HelperClass.getBase64Image(movie);

                Label titleLabel = new Label(movie.getMovieName());

                movieItem.getChildren().addAll(imageView, titleLabel);

                movieItem.setOnMouseClicked(event -> {
                    MovieDetailsScreen movieDetailsScreen = new MovieDetailsScreen(movie, mainMovieController.getUser(), this);
                    primaryStage.close();
                    movieDetailsScreen.showAndWait();
                });

                movieList.getChildren().add(movieItem);
            }
        } else showDeleteFailDialog("somthing Wrong", "Something Wrong");


        ScrollPane scrollPane = new ScrollPane(movieList);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefViewportWidth(600);

        root.getChildren().add(scrollPane);

        Button showUsersButton = new Button("Show All Users");
        Button uploadNewMovieButton = new Button("Upload New Movie");
        Button searchMovieButton = new Button("Search Movie");
        Button logoutButton = new Button("Logout");
        Button watchListButton = new Button("My Watch List");

        watchListButton.setOnAction(e->{
            if(!mainMovieController.getUser().getUserMovieWatchList().isEmpty()) {
                WatchListScreen watchListScreen = new WatchListScreen(mainMovieController.getUser(), this);
                watchListScreen.show();
            }
            else DialogManager.showErrorDialog("Empty Watch List","Your Watch List is Empty,Please Add Movies");
        });


        uploadNewMovieButton.setOnAction(e -> {
            if (mainMovieController.getUser().getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                MovieUploader movieUploader = null;
                try {
                    movieUploader = new MovieUploader(mainMovieController.getUser());
                    movieUploader.getMovieUploaderController().setPreviousScreen(this);
                    movieUploader.start(primaryStage);
                } catch (Exception e1) {
                    throw new RuntimeException(e1);
                }
            } else showNoPermissionDialog();
        });

        logoutButton.setOnAction(e -> {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
            LoginScreenView loginScreen = new LoginScreenView();
            try {
                loginScreen.start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException("Error opening login screen", ex);
            }
        });
        root.getChildren().add(logoutButton);

        showUsersButton.setOnAction(e -> {
            if (mainMovieController.getUser().getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                List<User> usersList = MainMovieController.getUsersInfoFromServer("get all users");

                ListView<User> userListView = new ListView<>();
                userListView.getItems().addAll(usersList);
                userListView.setPrefSize(300, 200);

                userListView.setCellFactory(param -> new ListCell<>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (empty || user == null) {
                            setText(null);
                        } else {
                            setText("Username  : " + user.getUserName() + ",\tPassword  : " + user.getUserPassword() + ",\t permission : " + user.getPermissionLevel());
                            setTextFill(Color.BLUE);
                            setAlignment(Pos.CENTER);
                        }
                    }
                });

                Button deleteButton = new Button("Delete");
                deleteAction(deleteButton, userListView);

                VBox vBox = new VBox(10, userListView, deleteButton);
                vBox.setAlignment(Pos.CENTER);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("All Users Information");
                alert.setHeaderText(null);
                alert.getDialogPane().setContent(vBox);
                alert.showAndWait();
            } else {
                showNoPermissionDialog();
            }
        });
        root.getChildren().add(showUsersButton);

        Button searchRandomMovieButton = new Button("Search Random Movie");
        searchRandomMovieButton.setOnAction(e -> {
            // Open a new window for selecting a genre and searching for a random movie
            GenreSelectionScreen genreSelectionScreen = new GenreSelectionScreen();
            genreSelectionScreen.showAndWait();
        });
        root.getChildren().add(searchRandomMovieButton);


        searchMovieButton.setOnAction(e -> {
            SearchForMovie searchForMovie = null;
            try {
                searchForMovie = new SearchForMovie();
                searchForMovie.setPreviousScreen(this);
                searchForMovie.start(primaryStage);
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        });


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(uploadNewMovieButton, searchMovieButton,watchListButton);
        root.getChildren().add(buttonBox);

        root.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void showNoPermissionDialog() {
        showDeleteFailDialog("Permission Denied", "You do not have permission to perform this action.");
    }


    @Override
    public void refreshData(User user) {
        String action = "get user by id";
        Map<String, Object> body = new HashMap<>();
        body.put("User", user.getUserId());
        try {
            Client.saveToServer(new Request(action, body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");
            System.exit(0);
        }
        try {
            mainMovieController.setUser(Client.receiveUserFromServer());
            Client.saveToServer(new Request("get all movies"));
            mainMovieController.setAllMovies(Client.receiveListFromServer(new TypeToken<List<Movie>>() {
            }));
        } catch (IOException e) {
            DialogManager.showDeleteFailDialog("Error", "Error To Refresh");
        }
    }




}
