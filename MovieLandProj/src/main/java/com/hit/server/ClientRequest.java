package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.algorithm.RabinCarpStringMatchingImpl;
import com.hit.controller.MovieController;
import com.hit.controller.UserController;
import com.hit.dao.MovieFileImpl;
import com.hit.dm.movie.Movie;
import com.hit.dm.user.User;
import com.hit.service.MovieService;
import com.hit.service.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

public class ClientRequest {

    private UserController userController;
    private MovieController movieController;
    private Socket m_someClient;
    private Gson gson = new GsonBuilder().create();

    public ClientRequest(Socket m_someClient) throws IOException {
        this.movieController = new MovieController(new MovieService(new RabinCarpStringMatchingImpl(),new MovieFileImpl()));
        this.userController = new UserController(new UserService());
        this.m_someClient = m_someClient;
    }

    public void process() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(m_someClient.getInputStream()));
            String gsonFormat = reader.readLine();
            Request request = gson.fromJson(gsonFormat , Request.class);
            String action = request.getAction();
            Map<String,Object> body = request.getBody();
            makeAction(action, body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeAction(String action, Map<String, Object> body) {
        switch (action){
            case "register":
                String bodyStr = gson.toJson(body);
                User user = gson.fromJson(bodyStr, User.class);
                try {
                    userController.register(user);
                } catch (Exception e) {
                    System.out.println("cant register");
                }

        }
    }
}
