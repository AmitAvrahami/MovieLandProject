package org.example.movieclient.UI;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.movieclient.model.user.User;

public class DialogManager {
    public static void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showLoginFailDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Incorrect Username or Password");
        alert.setContentText("Please check your username and password and try again.");
        alert.setOnCloseRequest(event -> {
            alert.close();
        });
        alert.showAndWait();
    }

    public static void showDeleteFailDialog(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }

    public static void showSuccessDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void showRegisterFailedDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Failed");
        alert.setHeaderText("Empty Username or Password , or this user id is exist already");
        alert.setContentText("Please enter both username and password to register.");
        alert.showAndWait();
    }

    public static void showRegisterSuccessDialog(int userId) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("User registered successfully! User ID: " + userId);
        alert.showAndWait();
    }

    public static void showErrorUploadMovieAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Movie Upload");
        alert.setHeaderText(null);
        alert.setContentText("Error: Movie could not be uploaded.");
        alert.showAndWait();
    }

    public static void showSuccessUploadMovieAlert(Stage stage, User user) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Movie Upload");
        alert.setHeaderText(null);
        alert.setContentText("Movie uploaded successfully!");
        alert.showAndWait();
    }
}
