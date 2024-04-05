package com.hit.dm;

import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.user.PermissionLevel;
import com.hit.dm.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDataSample {

    // Creating movies
    private static final Movie movie1 = new Movie(1, "The Avengers", null, "Action-packed superhero movie", MovieCategory.ACTION, "2h 23m", null);
    private static final Movie movie2 = new Movie(2, "The Shawshank Redemption", null, "Drama film about hope and redemption", MovieCategory.DRAMA, "2h 22m", null);
    private static final Movie movie3 = new Movie(3, "Inception", null, "Mind-bending science fiction thriller", MovieCategory.FAMILY, "2h 28m", null);
    private static final Movie movie4 = new Movie(4, "The Godfather", null, "Classic crime film", MovieCategory.CRIME, "2h 55m", null);
    private static final Movie movie5 = new Movie(5, "Titanic", null, "Epic romance disaster film", MovieCategory.ROMANCE, "3h 14m", null);

    // Creating users
    public static List<User> getSampleUsers() {
        List<User> users = new ArrayList<>();

        // User 1
        HashMap<Movie, Boolean> user1WatchList = new HashMap<>();
        user1WatchList.put(movie1, true);
        user1WatchList.put(movie2, true);
        user1WatchList.put(movie3, false);
        User user1 = new User(1, "user1", "password1", PermissionLevel.USER, user1WatchList);
        users.add(user1);

        // User 2
        HashMap<Movie, Boolean> user2WatchList = new HashMap<>();
        user2WatchList.put(movie2, true);
        user2WatchList.put(movie3, true);
        user2WatchList.put(movie4, false);
        User user2 = new User(2, "user2", "password2", PermissionLevel.USER, user2WatchList);
        users.add(user2);

        // User 3
        HashMap<Movie, Boolean> user3WatchList = new HashMap<>();
        user3WatchList.put(movie3, true);
        user3WatchList.put(movie4, true);
        user3WatchList.put(movie5, false);
        User user3 = new User(3, "user3", "password3", PermissionLevel.USER, user3WatchList);
        users.add(user3);

        // User 4
        HashMap<Movie, Boolean> user4WatchList = new HashMap<>();
        user4WatchList.put(movie4, true);
        user4WatchList.put(movie5, true);
        user4WatchList.put(movie1, false);
        User user4 = new User(4, "user4", "password4", PermissionLevel.USER, user4WatchList);
        users.add(user4);

        // User 5
        HashMap<Movie, Boolean> user5WatchList = new HashMap<>();
        user5WatchList.put(movie5, true);
        user5WatchList.put(movie1, true);
        user5WatchList.put(movie2, false);
        User user5 = new User(5, "user5", "password5", PermissionLevel.USER, user5WatchList);
        users.add(user5);

        return users;
    }
}
