package com.hit.dm;

import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;

import java.util.ArrayList;
import java.util.List;

public class MovieDataSample {

    // Creating actors
    private static final Actor actor1 = new Actor(1, "John", "Doe", 30, new ArrayList<>());
    private static final Actor actor2 = new Actor(2, "Jane", "Doe", 28, new ArrayList<>());
    private static final Actor actor3 = new Actor(3, "Alice", "Smith", 35, new ArrayList<>());
    private static final Actor actor4 = new Actor(4, "Bob", "Johnson", 40, new ArrayList<>());
    private static final Actor actor5 = new Actor(5, "Emily", "Wilson", 25, new ArrayList<>());

    // Creating movies
    public static List<Movie> getSampleMovies() {
        List<Movie> movies = new ArrayList<>();

        // Movie 1
        List<Actor> movie1Actors = new ArrayList<>();
        movie1Actors.add(actor1);
        movie1Actors.add(actor2);
        Movie movie1 = new Movie(1, "The Avengers", new ArrayList<>(), "Action-packed superhero movie", MovieCategory.ACTION, "2h 23m", movie1Actors);
        movies.add(movie1);

        // Movie 2
        List<Actor> movie2Actors = new ArrayList<>();
        movie2Actors.add(actor3);
        movie2Actors.add(actor4);
        Movie movie2 = new Movie(2, "The Shawshank Redemption", new ArrayList<>(), "Drama film about hope and redemption", MovieCategory.DRAMA, "2h 22m", movie2Actors);
        movies.add(movie2);

        // Movie 3
        List<Actor> movie3Actors = new ArrayList<>();
        movie3Actors.add(actor1);
        movie3Actors.add(actor5);
        Movie movie3 = new Movie(3, "Inception", new ArrayList<>(), "Mind-bending science fiction thriller", MovieCategory.FAMILY, "2h 28m", movie3Actors);
        movies.add(movie3);

        // Movie 4
        List<Actor> movie4Actors = new ArrayList<>();
        movie4Actors.add(actor2);
        movie4Actors.add(actor4);
        Movie movie4 = new Movie(4, "The Godfather", new ArrayList<>(), "Classic crime film", MovieCategory.CRIME, "2h 55m", movie4Actors);
        movies.add(movie4);

        // Movie 5
        List<Actor> movie5Actors = new ArrayList<>();
        movie5Actors.add(actor3);
        movie5Actors.add(actor5);
        Movie movie5 = new Movie(5, "Titanic", new ArrayList<>(), "Epic romance disaster film", MovieCategory.ROMANCE, "3h 14m", movie5Actors);
        movies.add(movie5);

        return movies;
    }
}
