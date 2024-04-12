package org.example.movieclient.UI.Login;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.movieclient.UI.Register.RegisterScreenView;
import org.example.movieclient.model.user.User;

import java.util.*;

import static org.example.movieclient.UI.DialogManager.showLoginFailDialog;

public class LoginScreenView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Welcome To Movie Land");
        titleLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(titleLabel, 0, 0, 2, 1);

        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);

        TextField usernameTextField = new TextField();
        grid.add(usernameTextField, 1, 1);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        VBox vbox = new VBox(10, grid, loginButton, registerButton);
        vbox.setAlignment(Pos.CENTER);

        loginButton.setOnAction(e -> {
            String action = "Login";
            Map<String,Object> body = new HashMap<>();
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            User user =  LoginController.loginRequest(username, password, body, action);
            if (Objects.nonNull(user)){
                LoginController.openMainScene(primaryStage,user);
            }
            else {
                showLoginFailDialog();
            }
        });

            registerButton.setOnAction(e1 -> {
                RegisterScreenView registerScreenView = null;
                try {
                     registerScreenView = new RegisterScreenView();
                    registerScreenView.getRegisterController().setPreviousScreen(this);
                    registerScreenView.start(primaryStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

        });

        Scene scene = new Scene(vbox, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }







}
