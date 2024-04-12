package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.algorithm.KmpAlgoStringMatchingImpl;
import com.hit.controller.MovieController;
import com.hit.controller.UserController;
import com.hit.dao.MovieFileImpl;
import com.hit.service.MovieService;
import com.hit.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;


public class HandleRequest {

    private UserController userController;

    private static final String LOCAL_HOST = "localhost";
    private static final int PORT = 34567;
    private MovieController movieController;
    private Response response;
    private static Socket m_someClient;
    private static Gson gson = new GsonBuilder().create();

    HandleRequest(Socket m_someClient) throws IOException {
        this.movieController = new MovieController(new MovieService(new KmpAlgoStringMatchingImpl(), new MovieFileImpl()));
        this.userController = new UserController(new UserService());
        this.m_someClient = m_someClient;
        this.response = new Response(userController, movieController, gson);

    }

    public void process() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(m_someClient.getInputStream()));
            String gsonFormat = reader.readLine();
            Request request = gson.fromJson(gsonFormat, Request.class);
            String action = request.getAction();
            Map<String, Object> body = request.getBody();
            if (body != null) makeAction(action, body);
            else makeActionWithoutBody(action);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessageToClient(Object message) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(m_someClient.getOutputStream(), true);
            String x = gson.toJson(message);
            writer.println(x);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeActionWithoutBody(String action) {
        System.out.println(action);
        switch (action) {
            case "get movies Names":
                response.sentMoviesNames();
                break;
            case "get movies Descriptions":
                response.sentMoviesDescriptions();
                break;
            case "get movies Images":
                response.sentMoviesImages();
                break;
            case "get all movies":
                response.sentAllMovies();
                break;
            case "get all users":
                response.sentAllUsers();
                break;
            case "get last user id":
                response.getLastUserIdExecute();
        }
    }

    private void makeAction(String action, Map<String, Object> body) throws IOException {
        System.out.println(action);
        System.out.println(body);
        switch (action) {
            case "Register":
                response.registerExecute(body);
                break;
            case "Login":
                response.loginExecute(body);
                break;
            case "Upload Movie":
                response.uploadMovieExecute(body);
                break;
            case "search by movie name":
                response.searchMovieByNameExecute(body);
                break;
            case "search movies by actor":
                response.SearchMoviesByActorNameExecute(body);
                break;
            case "search movies by category":
                response.SearchMoviesByCategoryExecute(body);
                break;
            case "delete user":
                response.deleteUserFromDb(body);
                break;
            case "add movie to watch list":
                response.addMovieToWatchListExecute(body);
                break;
            case "get user by id":
                response.getUserById(body);
                break;
            case "choose random movie":
                response.chooseRandomMovieExecute(body);
                break;
            case "remove movie":
                response.removeMovieExecute(body);
                break;
            case "remove movie from watch list":
                response.removeMovieFromWatchListExecute(body);

        }
    }


}

