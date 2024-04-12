package com.hit.server;


import com.google.gson.Gson;
import com.hit.controller.MovieController;
import com.hit.controller.UserController;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieProperty;
import com.hit.dm.user.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.hit.server.HandleRequest.sendMessageToClient;

public class Response {
    private UserController userController;
    private MovieController movieController;
    private Gson gson;

    public Response(UserController userController, MovieController movieController, Gson gson) {
        this.userController = userController;
        this.movieController = movieController;
        this.gson = gson;
    }

    public void removeMovieExecute(Map<String, Object> body) {
        String movieJasonFormat = gson.toJson(body.get("Movie"));
        Movie movie = gson.fromJson(movieJasonFormat, Movie.class);
        try {
            movieController.removeMovie(movie);
            sendMessageToClient(true);
            byte[] imageData = java.util.Base64.getDecoder().decode(movie.getMovieImage());
            String fileExtension = getImageType(movie.getMovieImage());
            String fileName = movie.getMovieName().replaceAll("\\s+", "_") + "." + fileExtension;
            String filePath = "MovieLandProj/src/main/resources/images/" + fileName;
            Files.deleteIfExists(Paths.get(filePath));
        } catch (Exception e) {
            sendMessageToClient(false);
        }
    }

    public void chooseRandomMovieExecute(Map<String, Object> body) {
        MovieCategory movieCategory = MovieCategory.valueOf((String) body.get("Category"));
        Movie movie = movieController.randomSelectionOfMovieByCategory(movieCategory);
        sendMessageToClient(movie);
    }

    public void getLastUserIdExecute() {
        try {
            Integer lastUserId = userController.getLastUserId();
            sendMessageToClient(lastUserId);
        } catch (Exception e) {
            sendMessageToClient(-1); // Return -1 if there's an error
        }
    }

    public void getUserById(Map<String, Object> body) {
        String userJasonFormat = gson.toJson(body.get("User"));
        Integer id = gson.fromJson(userJasonFormat, Integer.class);
        try {
            User user = userController.getUserById(id);
            sendMessageToClient(user);
        } catch (IOException | ClassNotFoundException e) {
            sendMessageToClient(null);
        }
    }

    public void addMovieToWatchListExecute(Map<String, Object> body) {
        try {
            // Retrieve the pair of Movie and User from the body map
            List<Object> pair = (List<Object>) body.get("movie+user");
            String movieJasonFormat = gson.toJson(pair.get(0));
            Movie movie = gson.fromJson(movieJasonFormat, Movie.class);
            String userJasonFormat = gson.toJson(pair.get(1));
            User user = gson.fromJson(userJasonFormat, User.class);
            userController.addToWatchlist(user, movie);
            sendMessageToClient(true);
        } catch (Exception e) {
            sendMessageToClient(false);
        }
    }

    public void deleteUserFromDb(Map<String, Object> body) {
        String userToDeleteStr = gson.toJson(body.get("User"));
        User userToDelete = gson.fromJson(userToDeleteStr, User.class);
        try {
            userController.deleteUser(userToDelete);
            sendMessageToClient(true);
        } catch (Exception e) {
            sendMessageToClient(false);
        }
    }

    public void sentAllUsers() {
        try {
            List<User> allUsers = userController.getAllUsersFromDb();
            sendMessageToClient(allUsers);
        } catch (IOException | ClassNotFoundException e) {
            sendMessageToClient(null);
            e.getMessage();
        }
    }

    public void SearchMoviesByCategoryExecute(Map<String, Object> body) {
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

    public void SearchMoviesByActorNameExecute(Map<String, Object> body) {
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

    public void sentMoviesImages() {
        List<String> movieImage = (List<String>) movieController.getAllMovieProperty(MovieProperty.IMAGE);
        sendMessageToClient(movieImage);
    }

    public void sentMoviesDescriptions() {
        List<String> movieDes = (List<String>) movieController.getAllMovieProperty(MovieProperty.SYNOPSIS);
        sendMessageToClient(movieDes);
    }

    public void sentAllMovies() {
        try {
            List<Movie> allMovies = (List<Movie>) movieController.getAllMoviesFromDb();
            sendMessageToClient(allMovies);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchMovieByNameExecute(Map<String, Object> body) {
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

    public void sentMoviesNames() {
        List<String> movieNames = (List<String>) movieController.getAllMovieProperty(MovieProperty.NAME);
        sendMessageToClient(movieNames);
    }

    public void uploadMovieExecute(Map<String, Object> body) {
        try {
            String movieJson = gson.toJson(body.get("Movie"));
            Movie movieToUpload = gson.fromJson(movieJson, Movie.class);
            if (movieToUpload.getMovieImage().isEmpty()) {
                movieToUpload.setMovieImage(getDefaultImage());
            }
            String base64Image = movieToUpload.getMovieImage();
            byte[] imageData = Base64.getDecoder().decode(base64Image);
            String fileExtension = getImageType(movieToUpload.getMovieImage());
            String fileName = movieToUpload.getMovieName().replaceAll("\\s+", "_") + "." + fileExtension;// Assuming the image is in JPEG format
            File filePath = new File("MovieLandProj/src/main/resources/images/" + fileName);
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(imageData);
            outputStream.close();
            movieController.addMovie(movieToUpload);
            sendMessageToClient(true);

        } catch (Exception e) {
            sendMessageToClient(false);
        }
    }

    public void loginExecute(Map<String, Object> body) throws IOException {
        List<String> userDetails = (List<String>) body.get("userName+password");
        if (userDetails != null && userDetails.size() == 2) {
            String username = userDetails.get(0);
            String password = userDetails.get(1);
            try {
                User isUserExist = userController.login(username, password);
                System.out.println(isUserExist.toString());
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

    public void registerExecute(Map<String, Object> body) {
        String bodyStr = gson.toJson(body.get("User"));
        User user = gson.fromJson(bodyStr, User.class);
        try {
            userController.register(user);
            sendMessageToClient(true);
        } catch (Exception e) {
            System.out.println("cant register");
            sendMessageToClient(false);
        }
    }

    public String getDefaultImage() {
        String filePath = "MovieLandProj/src/main/resources/images/empty_image_dont_touch.jpeg";
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);
            return base64Image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getImageType(String base64String) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);
            bis.close();

            if (image == null) {
                return "unknown";
            }

            String formatName = "";
            for (String format : ImageIO.getWriterFormatNames()) {
                if (ImageIO.write(image, format, new ByteArrayOutputStream())) {
                    formatName = format;
                    break;
                }
            }
            return formatName;
        } catch (IOException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    public void removeMovieFromWatchListExecute(Map<String, Object> body) {
        String movieJasonFormat = gson.toJson(body.get("Movie"));
        String userJasonFormat = gson.toJson(body.get("User"));
        Movie movie = gson.fromJson(movieJasonFormat, Movie.class);
        User user = gson.fromJson(userJasonFormat, User.class);
        try {
            userController.removeFromWatchlist(user, movie);
            sendMessageToClient(true);
        } catch (Exception e) {
            sendMessageToClient(false);
        }
    }
}
