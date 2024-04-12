package org.example.movieclient.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HelperClass {


    public static List<String> getPropertiesFromServer(String action) {
        try {
            Client.saveToServer(new Request(action));
            List<String> properties = (List<String>) Client.receiveFromServer();
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Error fetching data from server", e);
        }
    }


    public static ImageView getBase64Image(Movie movie) {
        String base64Image = movie.getMovieImage();
        byte[] imageData = Base64.getDecoder().decode(base64Image);
        Image image = new Image(new ByteArrayInputStream(imageData));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private static void showSuccessAlert(Stage stage, User user) {
        DialogManager.showSuccessUploadMovieAlert(stage, user);
        stage.close();
        try {
            MainMovieScreenView mainMovieScreenView = new MainMovieScreenView(user);
            mainMovieScreenView.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void movieUploadMessage(boolean isMovieUpload, Stage stage, User user) {
        if (isMovieUpload) {
            showSuccessAlert(stage, user);
        } else {
            DialogManager.showErrorUploadMovieAlert();
        }
    }

    public static boolean UploadMovieToServer(Movie newMovieToUpload) {
        String action = "Upload Movie";
        boolean messageFromServer = false;
        Map<String, Object> body = new HashMap<>();
        body.put("Movie", newMovieToUpload);
        Request request = new Request(action, body);
        try {
            Client.saveToServer(request);
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error", "The Is Problem With Connection To The Server");
        }
        try {
            messageFromServer = (boolean) Client.receiveFromServer();
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error", "The Is Problem With Connection To The Server");
        }
        return messageFromServer;
    }


}
