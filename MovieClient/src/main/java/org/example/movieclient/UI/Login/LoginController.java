package org.example.movieclient.UI.Login;

import javafx.stage.Stage;
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginController {

    public static User loginRequest(String username, String password, Map<String, Object> body, String action) {
        List<String> userDetails = new ArrayList<>();
        User user = null;
        userDetails.add(username);
        userDetails.add(password);
        body.put("userName+password" , userDetails);
        Request request = new Request(action, body);
        try {
            Client.saveToServer(request);


        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server , Check Your Connection");
            System.exit(0);
        }
        try {
            user = Client.receiveUserFromServer();
        } catch (IOException ex) {
            DialogManager.showLoginFailDialog();
        }
        return user;
    }

    public static void openMainScene(Stage primaryStage, User user) {
        MainMovieScreenView mainMovieScreenView = null;
        try {
            mainMovieScreenView = new MainMovieScreenView(user);
            mainMovieScreenView.start(primaryStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
