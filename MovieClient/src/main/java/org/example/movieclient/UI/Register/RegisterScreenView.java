package org.example.movieclient.UI.Register;

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
import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.UI.Login.LoginScreenView;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.user.PermissionLevel;
import org.example.movieclient.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterScreenView extends Application {
  private RegisterController registerController = new RegisterController();



    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Register New User");
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

        Label isAdmin = new Label("You Are Admin?");
        grid.add(isAdmin, 1, 3);

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton yesRb = new RadioButton("Yes");
        RadioButton noRb = new RadioButton("No");
        yesRb.setToggleGroup(toggleGroup);
        noRb.setToggleGroup(toggleGroup);
        grid.add(yesRb, 1, 4);
        grid.add(noRb, 1, 5);

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        registerButton.setOnAction(e -> {
            Toggle selectedToggle = toggleGroup.getSelectedToggle();
            if (selectedToggle != null) {
                RadioButton selectedRb = (RadioButton) selectedToggle;
                String selectedOption = selectedRb.getText();
                registerController.setIsAdminChoose(registerController.isAdminSelected(selectedOption));
            }
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            if(!username.isEmpty() && !password.isEmpty()) {
                registerController.setLastUserId(RegisterController.fetchLastUserIdFromServer());
                int newUserId = registerController.getLastUserId() + 1;
                User newUser = new User(newUserId, username, password, registerController.getIsAdminChoose(), new ArrayList<>());
                registerController.setAction("Register");
                registerController.setBody(new HashMap<>());
                registerController.getBody().put("User", newUser);
                Request request = new Request(registerController.getAction(), registerController.getBody());
                try {
                    Client.saveToServer(request);
                } catch (IOException ex) {
                    DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");
                    System.exit(0);
                }
                try {
                    if ((boolean)Client.receiveFromServer()){
                        DialogManager.showRegisterSuccessDialog(newUserId);
                    }
                    else  DialogManager.showRegisterFailedDialog();
                } catch (IOException ex) {
                    DialogManager.showRegisterFailedDialog();
                }
            }
            else{
                DialogManager.showRegisterFailedDialog();
            }
        });

        backButton.setOnAction(e -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            if (registerController.getPreviousScreen() != null) {
                Stage loginStage = new Stage();
                registerController.getPreviousScreen().start(loginStage);
            }
        });

        VBox vbox = new VBox(10, grid, registerButton, backButton);
        vbox.setAlignment(Pos.CENTER);

        registerController.setScene(new Scene(vbox, 300, 300));
        primaryStage.setScene(registerController.getScene());
        primaryStage.show();
    }
    public RegisterController getRegisterController() {
        return registerController;
    }

    public void setRegisterController(RegisterController registerController) {
        this.registerController = registerController;
    }






}
