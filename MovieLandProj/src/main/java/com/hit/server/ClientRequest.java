package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.algorithm.RabinCarpStringMatchingImpl;
import com.hit.controller.MovieController;
import com.hit.controller.UserController;
import com.hit.dao.MovieFileImpl;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieProperty;
import com.hit.dm.user.User;
import com.hit.service.MovieService;
import com.hit.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.*;


public class ClientRequest {

    protected UserController userController;

    private static final String LOCAL_HOST = "localhost";
    private static final int PORT = 34567;
    protected MovieController movieController;
    protected  Socket m_someClient;
    protected  Gson gson = new GsonBuilder().create();
    ClientRequest(Socket m_someClient) throws IOException {
        this.movieController = new MovieController(new MovieService(new RabinCarpStringMatchingImpl(), new MovieFileImpl()));
        this.userController = new UserController(new UserService());
        this.m_someClient = m_someClient;
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

    public  void sendMessageToClient(Object message) {
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
                sentMoviesNames();
                break;
            case "get movies Descriptions":
                sentMoviesDescriptions();
                break;
            case "get movies Images":
                sentMoviesImages();
                break;
            case "get all movies":
               sentAllMovies();
                break;
            case "get all users":
                sentAllUsers();

        }
    }



    private void makeAction(String action, Map<String, Object> body) throws IOException {
        System.out.println(action);
        System.out.println(body);
        switch (action) {
            case "Register":
                registerExecute(body);
                break;
            case "Login":
                loginExecute(body);
                break;
            case "Upload Movie":
                uploadMovieExecute(body);
                break;
            case "search by movie name":
                searchMovieByNameExecute(body);
                break;
            case "search movies by actor":
                SearchMoviesByActorNameExecute(body);
                break;
            case "search movies by category":
                SearchMoviesByCategoryExecute(body);
            case "delete user":
                deleteUserFromDb(body);

        }
    }

    private void deleteUserFromDb(Map<String, Object> body) {
        String userToDeleteStr = gson.toJson(body.get("User"));
        User userToDelete = gson.fromJson(userToDeleteStr,User.class);
        try {
            userController.deleteUser(userToDelete);
            sendMessageToClient(true);
        } catch (Exception e) {
            sendMessageToClient(false);
        }
    }

    private void sentAllUsers() {
        try {
            List<User> allUsers =  userController.getAllUsersFromDb();
            sendMessageToClient(allUsers);
        } catch (IOException | ClassNotFoundException e) {
            sendMessageToClient(null);
            e.getMessage();
        }
    }

    private void SearchMoviesByCategoryExecute(Map<String, Object> body) {
        String bodyStr = (String) body.get("Category");
        MovieCategory movieGenre = MovieCategory.valueOf(bodyStr);
        List<Movie> movieList;
        try {
            movieList = movieController.searchMoviesByGenre(movieGenre);
            sendMessageToClient(movieList);
        } catch (Exception e) {
            sendMessageToClient(new ArrayList<Movie>());
        }
    }

    private void SearchMoviesByActorNameExecute(Map<String, Object> body) {
        String bodyStr = gson.toJson(body.get("Actor Name"));
        String actorNameFromUser = gson.fromJson(bodyStr, String.class);
        List<Movie> movieList;
        try {
            movieList = movieController.getMoviesByActorFullName(actorNameFromUser);
            sendMessageToClient(movieList);
        } catch (Exception e) {
            sendMessageToClient(new ArrayList<Movie>());
        }
    }


    private   void sentMoviesImages() {
        List<String> movieImage = (List<String>) movieController.getAllMovieProperty(MovieProperty.IMAGE);
        sendMessageToClient(movieImage);
    }

    private void sentMoviesDescriptions() {
        List<String> movieDes = (List<String>) movieController.getAllMovieProperty(MovieProperty.SYNOPSIS);
        sendMessageToClient(movieDes);
    }


    private  void sentAllMovies() {
        try {
            List<Movie> allMovies = (List<Movie>) movieController.getAllMoviesFromDb();
            sendMessageToClient(allMovies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private  void searchMovieByNameExecute(Map<String, Object> body) {
        String bodyStr = gson.toJson(body.get("Movie Name"));
        String movieNameFromUser = gson.fromJson(bodyStr, String.class);
        List<Movie> movieListByName;
        try {
            movieListByName = movieController.searchMoviesByMovieName(movieNameFromUser);
            sendMessageToClient(movieListByName);
        } catch (Exception e) {
            sendMessageToClient(new ArrayList<Movie>());
        }
    }

    private  void sentMoviesNames() {
        List<String> movieNames = (List<String>) movieController.getAllMovieProperty(MovieProperty.NAME);
        sendMessageToClient(movieNames);
    }


    private void uploadMovieExecute(Map<String, Object> body) {
        try {
            String movieJson = gson.toJson(body.get("Movie"));
            Movie movieToUpload = gson.fromJson(movieJson, Movie.class);
            String base64Image = movieToUpload.getMovieImage();
            byte[] imageData = Base64.getDecoder().decode(base64Image);
            String fileExtension = getFileExtension(movieToUpload.getMovieImage());
            String fileName = movieToUpload.getMovieName();
            fileName =fileName.replaceAll("\\s+", "_") + "." + fileExtension;// Assuming the image is in JPEG format
            File filePath = new File("MovieLandProj/src/main/resources/images/" + fileName);
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(imageData);
            outputStream.close();
            movieController.addMovie(movieToUpload);
            sendMessageToClient(true);
        } catch (Exception e) {
            // Send error message to the client
            e.printStackTrace();
            sendMessageToClient(false);
        }
    }
    private String getFileExtension(String base64Image) {
        String[] parts = base64Image.split(",");
        String header = parts[0];
        String[] typeParts = header.split("/");
        String fileExtension = typeParts[1].split(";")[0];
        return fileExtension;
    }


    private  void loginExecute(Map<String, Object> body) throws IOException {
        List<String> userDetails = (List<String>) body.get("userName+password");
        if (userDetails != null && userDetails.size() == 2) {
            String username = userDetails.get(0);
            String password = userDetails.get(1);
            try {
                User isUserExist = userController.login(username, password);
                sendMessageToClient(isUserExist);
                System.out.println("User Logged In");
            } catch (Exception e) {
                System.out.println("Failed to login: " + e.getMessage());
                sendMessageToClient(null);
            }
        } else {
            System.out.println("Invalid format for username and password.");
            sendMessageToClient(null);
        }
    }

    private  void registerExecute(Map<String, Object> body) {
        String bodyStr = gson.toJson(body.get("User"));
        User user = gson.fromJson(bodyStr, User.class);
        try {
            userController.register(user);
        } catch (Exception e) {
            System.out.println("cant register");
        }
    }


}

