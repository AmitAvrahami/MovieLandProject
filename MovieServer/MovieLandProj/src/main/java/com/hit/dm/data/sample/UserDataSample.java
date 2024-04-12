package com.hit.dm.data.sample;

import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.user.PermissionLevel;
import com.hit.dm.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataSample {

    // Creating movies
    private static final Movie movie1 = new Movie(1, "The Avengers",  "Action-packed superhero movie", MovieCategory.ACTION, "2h 23m", null);
    private static final Movie movie2 = new Movie(2, "The Shawshank Redemption", "Drama film about hope and redemption", MovieCategory.DRAMA, "2h 22m", null);
    private static final Movie movie3 = new Movie(3, "Inception",  "Mind-bending science fiction thriller", MovieCategory.FAMILY, "2h 28m", null);
    private static final Movie movie4 = new Movie(4, "The Godfather", "Classic crime film", MovieCategory.CRIME, "2h 55m", null);
    private static final Movie movie5 = new Movie(5, "Titanic", "Epic romance disaster film", MovieCategory.ROMANCE, "3h 14m", null);

    // Creating users
    public static List<User> getSampleUsers() {
        List<User> users = new ArrayList<>();

        // User 1
        List<Movie> user1WatchList = new ArrayList<>();
        user1WatchList.add(movie1);
        user1WatchList.add(movie2);
        user1WatchList.add(movie3);
        User user1 = new User(1, "user1", "password1", PermissionLevel.USER, user1WatchList);
        users.add(user1);

        // User 2
        List<Movie> user2WatchList = new ArrayList<>();
        user2WatchList.add(movie2);
        user2WatchList.add(movie3);
        user2WatchList.add(movie4);
        User user2 = new User(2, "user2", "password2", PermissionLevel.USER, user2WatchList);
        users.add(user2);

        // User 3
        List<Movie> user3WatchList = new ArrayList<>();
        user3WatchList.add(movie3);
        user3WatchList.add(movie4);
        user3WatchList.add(movie5);
        User user3 = new User(3, "user3", "password3", PermissionLevel.USER, user3WatchList);
        users.add(user3);

        // User 4
        List<Movie> user4WatchList = new ArrayList<>();
        user4WatchList.add(movie4);
        user4WatchList.add(movie5);
        user4WatchList.add(movie1);
        User user4 = new User(4, "user4", "password4", PermissionLevel.USER, user4WatchList);
        users.add(user4);

        // User 5
        List<Movie> user5WatchList = new ArrayList<>();
        user5WatchList.add(movie5);
        user5WatchList.add(movie1);
        user5WatchList.add(movie2);
        User user5 = new User(5, "user5", "password5", PermissionLevel.USER, user5WatchList);
        users.add(user5);

        return users;
    }
}
