package org.example.movieclient.UI.MovieDetails;

import org.example.movieclient.UI.DialogManager;
import org.example.movieclient.client.Client;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDetailsController {


    public static void addToWishList(Movie movie, User user) {
        String action = "add movie to watch list";
        Map<String, Object> body = new HashMap<>();
        List<Object> pair = new ArrayList<>();
        pair.add(movie);
        pair.add(user);
        body.put("movie+user", pair);
        try {
            Client.saveToServer(new Request(action, body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");

        }
    }

    public static void removeMovie(Movie movie , User user) {
        String action = "remove movie";
        Map<String, Object> body = new HashMap<>();
        body.put("Movie", movie);
        try {
            Client.saveToServer(new Request(action, body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");
        }
    }


    public static void removeFromWatchList(Movie movie, User user ) {
        String action = "remove movie from watch list";
        Map<String, Object> body = new HashMap<>();
        body.put("Movie", movie);
        body.put("User", user);
        try {
            Client.saveToServer(new Request(action, body));
        } catch (IOException e) {
            DialogManager.showErrorDialog("Connection Error","The Is Problem With Connection To The Server");

        }
    }
}
