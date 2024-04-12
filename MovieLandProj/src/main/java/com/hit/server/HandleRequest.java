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
        this.response = new Response(userController,movieController,gson);

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
                response. deleteUserFromDb(body);
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

//    private void removeMovieFromWatchListExecute(Map<String, Object> body) {
//        String movieJasonFormat = gson.toJson(body.get("Movie"));
//        String userJasonFormat = gson.toJson(body.get("User"));
//        Movie movie = gson.fromJson(movieJasonFormat,Movie.class);
//        User user = gson.fromJson(userJasonFormat, User.class);
//        try {
//            userController.removeFromWatchlist(user,movie);
//            sendMessageToClient(true);
//        } catch (Exception e) {
//            sendMessageToClient(false);
//        }
//    }
//
//    private void removeMovieExecute(Map<String, Object> body) {
//        String movieJasonFormat = gson.toJson(body.get("Movie"));
//        Movie movie = gson.fromJson(movieJasonFormat, Movie.class);
//        try {
//            movieController.removeMovie(movie);
//            sendMessageToClient(true);
//            byte[] imageData = java.util.Base64.getDecoder().decode(movie.getMovieImage());
//            String fileExtension = getImageType(movie.getMovieImage());
//            String fileName = movie.getMovieName().replaceAll("\\s+", "_") + "." + fileExtension;
//            String filePath = "MovieLandProj/src/main/resources/images/" + fileName;
//            Files.deleteIfExists(Paths.get(filePath));
//        } catch (Exception e) {
//            sendMessageToClient(false);
//        }
//    }
//
//    private void chooseRandomMovieExecute(Map<String, Object> body) {
//        MovieCategory movieCategory = MovieCategory.valueOf((String) body.get("Category"));
//        Movie movie = movieController.randomSelectionOfMovieByCategory(movieCategory);
//        sendMessageToClient(movie);
//    }
//    private void getLastUserIdExecute() {
//        try {
//            Integer lastUserId = userController.getLastUserId();
//            sendMessageToClient(lastUserId);
//        } catch (Exception e) {
//            sendMessageToClient(-1); // Return -1 if there's an error
//        }
//    }
//
//    private void getUserById(Map<String, Object> body) {
//        String userJasonFormat = gson.toJson(body.get("User"));
//        Integer id = gson.fromJson(userJasonFormat, Integer.class);
//        try {
//           User user = userController.getUserById(id);
//           sendMessageToClient(user);
//        } catch (IOException | ClassNotFoundException e) {
//            sendMessageToClient(null);
//        }
//    }
//
//    private void addMovieToWatchListExecute(Map<String, Object> body) {
//        try {
//            // Retrieve the pair of Movie and User from the body map
//            List<Object> pair = (List<Object>) body.get("movie+user");
//            String  movieJasonFormat = gson.toJson(pair.get(0));
//            Movie movie = gson.fromJson(movieJasonFormat,Movie.class);
//            String userJasonFormat = gson.toJson(pair.get(1));
//            User user = gson.fromJson(userJasonFormat,User.class);
//            userController.addToWatchlist(user,movie);
//            sendMessageToClient(true);
//        } catch (Exception e) {
//            sendMessageToClient(false);
//        }
//    }
//    private void deleteUserFromDb(Map<String, Object> body) {
//        String userToDeleteStr = gson.toJson(body.get("User"));
//        User userToDelete = gson.fromJson(userToDeleteStr,User.class);
//        try {
//            userController.deleteUser(userToDelete);
//            sendMessageToClient(true);
//        } catch (Exception e) {
//            sendMessageToClient(false);
//        }
//    }
//    private void sentAllUsers() {
//        try {
//            List<User> allUsers =  userController.getAllUsersFromDb();
//            sendMessageToClient(allUsers);
//        } catch (IOException | ClassNotFoundException e) {
//            sendMessageToClient(null);
//            e.getMessage();
//        }
//    }
//    private void SearchMoviesByCategoryExecute(Map<String, Object> body) {
//        String bodyStr = (String) body.get("Category");
//        MovieCategory movieGenre = MovieCategory.valueOf(bodyStr);
//        List<Movie> movieList;
//        try {
//            movieList = movieController.searchMoviesByGenre(movieGenre);
//            sendMessageToClient(movieList);
//        } catch (Exception e) {
//            sendMessageToClient(new ArrayList<Movie>());
//        }
//    }
//    private void SearchMoviesByActorNameExecute(Map<String, Object> body) {
//        String bodyStr = gson.toJson(body.get("Actor Name"));
//        String actorNameFromUser = gson.fromJson(bodyStr, String.class);
//        List<Movie> movieList;
//        try {
//            movieList = movieController.getMoviesByActorFullName(actorNameFromUser);
//            sendMessageToClient(movieList);
//        } catch (Exception e) {
//            sendMessageToClient(new ArrayList<Movie>());
//        }
//    }
//    private   void sentMoviesImages() {
//        List<String> movieImage = (List<String>) movieController.getAllMovieProperty(MovieProperty.IMAGE);
//        sendMessageToClient(movieImage);
//    }
//    private void sentMoviesDescriptions() {
//        List<String> movieDes = (List<String>) movieController.getAllMovieProperty(MovieProperty.SYNOPSIS);
//        sendMessageToClient(movieDes);
//    }
//    private  void sentAllMovies() {
//        try {
//            List<Movie> allMovies = (List<Movie>) movieController.getAllMoviesFromDb();
//            sendMessageToClient(allMovies);
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private  void searchMovieByNameExecute(Map<String, Object> body) {
//        String bodyStr = gson.toJson(body.get("Movie Name"));
//        String movieNameFromUser = gson.fromJson(bodyStr, String.class);
//        List<Movie> movieListByName;
//        try {
//            movieListByName = movieController.searchMoviesByMovieName(movieNameFromUser);
//            sendMessageToClient(movieListByName);
//        } catch (Exception e) {
//            sendMessageToClient(new ArrayList<Movie>());
//        }
//    }
//    private  void sentMoviesNames() {
//        List<String> movieNames = (List<String>) movieController.getAllMovieProperty(MovieProperty.NAME);
//        sendMessageToClient(movieNames);
//    }
//    private void uploadMovieExecute(Map<String, Object> body) {
//        try {
//            String movieJson = gson.toJson(body.get("Movie"));
//            Movie movieToUpload = gson.fromJson(movieJson, Movie.class);
//            if (movieToUpload.getMovieImage().isEmpty()) {
//                movieToUpload.setMovieImage(getDefaultImage());
//            }
//                String base64Image = movieToUpload.getMovieImage();
//                byte[] imageData = Base64.getDecoder().decode(base64Image);
//                String fileExtension = getImageType(movieToUpload.getMovieImage());
//                String fileName = movieToUpload.getMovieName().replaceAll("\\s+", "_") + "." + fileExtension;// Assuming the image is in JPEG format
//                File filePath = new File("MovieLandProj/src/main/resources/images/" + fileName);
//                FileOutputStream outputStream = new FileOutputStream(filePath);
//                outputStream.write(imageData);
//                outputStream.close();
//                movieController.addMovie(movieToUpload);
//                sendMessageToClient(true);
//
//        } catch (Exception e) {
//            sendMessageToClient(false);
//        }
//    }
//
//    private  void loginExecute(Map<String, Object> body) throws IOException {
//        List<String> userDetails = (List<String>) body.get("userName+password");
//        if (userDetails != null && userDetails.size() == 2) {
//            String username = userDetails.get(0);
//            String password = userDetails.get(1);
//            try {
//                User isUserExist = userController.login(username, password);
//                System.out.println(isUserExist.toString());
//                sendMessageToClient(isUserExist);
//                System.out.println("User Logged In");
//            } catch (Exception e) {
//                System.out.println("Failed to login: " + e.getMessage());
//                sendMessageToClient(null);
//            }
//        } else {
//            System.out.println("Invalid format for username and password.");
//            sendMessageToClient(null);
//        }
//    }
//    private  void registerExecute(Map<String, Object> body) {
//        String bodyStr = gson.toJson(body.get("User"));
//        User user = gson.fromJson(bodyStr, User.class);
//        try {
//            userController.register(user);
//            sendMessageToClient(true);
//        } catch (Exception e) {
//            System.out.println("cant register");
//            sendMessageToClient(false);
//        }
//    }
//
//    private String getDefaultImage(){
//        String filePath = "MovieLandProj/src/main/resources/images/empty_image_dont_touch.jpeg";
//        try {
//            byte[]fileBytes = Files.readAllBytes(Paths.get(filePath));
//            String base64Image = Base64.getEncoder().encodeToString(fileBytes);
//            return base64Image;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String getImageType(String base64String) {
//        try {
//            byte[] imageBytes = Base64.getDecoder().decode(base64String);
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
//            BufferedImage image = ImageIO.read(bis);
//            bis.close();
//
//            if (image == null) {
//                return "unknown";
//            }
//
//            String formatName = "";
//            for (String format : ImageIO.getWriterFormatNames()) {
//                if (ImageIO.write(image, format, new ByteArrayOutputStream())) {
//                    formatName = format;
//                    break;
//                }
//            }
//            return formatName;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "unknown";
//        }
//    }


}

