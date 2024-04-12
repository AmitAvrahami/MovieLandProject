package org.example.movieclient.UI.Register;

import javafx.scene.Scene;
import org.example.movieclient.UI.Login.LoginScreenView;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.user.PermissionLevel;

import java.io.IOException;
import java.util.Map;

public class RegisterController {

    private Scene scene;
    private String action;
    private Map<String, Object> body;
    private PermissionLevel isAdminChoose = PermissionLevel.USER;
    private LoginScreenView previousScreen;
    private Integer lastUserId;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public PermissionLevel getIsAdminChoose() {
        return isAdminChoose;
    }

    public void setIsAdminChoose(PermissionLevel isAdminChoose) {
        this.isAdminChoose = isAdminChoose;
    }

    public LoginScreenView getPreviousScreen() {
        return previousScreen;
    }

    public void setPreviousScreen(LoginScreenView previousScreen) {
        this.previousScreen = previousScreen;
    }

    public Integer getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(Integer lastUserId) {
        this.lastUserId = lastUserId;
    }

    public static Integer fetchLastUserIdFromServer() {
        try {
            Client.saveToServer(new Request("get last user id"));
            Double aDouble = (Double) Client.receiveFromServer();
            int userId = aDouble.intValue();

            return userId;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public PermissionLevel isAdminSelected(String choose) {
        if (choose.equalsIgnoreCase("Yes")) {
            return PermissionLevel.ADMIN;
        } else {
            return PermissionLevel.USER;
        }
    }
}
