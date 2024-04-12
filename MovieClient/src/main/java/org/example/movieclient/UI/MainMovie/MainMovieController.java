package org.example.movieclient.UI.MainMovie;

import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.PermissionLevel;
import org.example.movieclient.model.user.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.movieclient.UI.DialogManager.showDeleteFailDialog;

public class MainMovieController {

    private User user;
    private static List<Movie> allMovies;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public  List<Movie> getAllMovies() {
        return allMovies;
    }

    public  void setAllMovies(List<Movie> allMovies) {
        MainMovieController.allMovies = allMovies;
    }



    public static List<User> getUsersInfoFromServer(String action) {
        try {
            Client.saveToServer(new Request(action));
            List<User> usersInfo = Client.receiveListFromServer(new TypeToken<List<User>>() {
            });
            return usersInfo;
        } catch (IOException e) {
            throw new RuntimeException("Error fetching users' information from server", e);
        }
    }

    public static void deleteAction(Button deleteButton, ListView<User> userListView) {
        deleteButton.setOnAction(event -> {
            User selectedUser = userListView.getSelectionModel().getSelectedItem();

            if (selectedUser != null ) {
                if (selectedUser.getPermissionLevel().equals(PermissionLevel.USER)) {
                    if (deleteUserFromServer("delete user", selectedUser)) {
                        userListView.getItems().remove(selectedUser);
                    } else {
                        showDeleteFailDialog("Delete Fail", "The Delete Doesnt Success");
                    }
                }
                else DialogManager.showErrorDialog("Illigal Operation","You can't Delete Admin User");
            } else {
                showDeleteFailDialog("No User Selected", "Please select a user to delete.");
            }
        });
    }

    private static boolean deleteUserFromServer(String action, User user) {
        Map<String, Object> body = new HashMap<>();
        boolean isDeleteSuccess = false;
        body.put("User", user);
        try {
            Client.saveToServer(new Request(action, body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");
        }
        try {
            isDeleteSuccess = (boolean) Client.receiveFromServer();
        } catch (IOException e) {
            isDeleteSuccess = false;
        }
        return isDeleteSuccess;
    }
}
