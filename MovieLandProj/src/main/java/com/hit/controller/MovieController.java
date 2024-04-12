package com.hit.controller;

import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieProperty;
import com.hit.dm.movie.MovieRateRange;
import com.hit.service.IMovieService;
import com.hit.service.MovieService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieController<T> implements IMovieService {


    private MovieService movieService;

    public MovieController(MovieService movieService) {

        this.movieService = movieService;
    }

    public List<Movie> searchMoviesByGenre(MovieCategory movieCategory) {
        return movieService.searchMoviesByGenre(movieCategory);
    }

    public List<Movie> searchMoviesByMovieName(String keyword) {
        return movieService.searchMoviesByMovieName(keyword);
    }

    public List<Actor> getActorsByMovie(Movie movie) {
        return movieService.getActorsByMovie(movie);
    }

    public List<Movie> getMoviesByActorFullName(String name) {
        return movieService.getMoviesByActorFullName(name);
    }

    public Movie randomSelectionOfMovieByCategory(MovieCategory movieCategory) {
        return movieService.randomSelectionOfMovieByCategory(movieCategory);
    }

    public List<Movie> getAllMoviesFromDb() throws IOException, ClassNotFoundException {
        return movieService.getAllMoviesFromDb();
    }



    public void removeMovie(Movie movieToRemove) throws Exception {
        movieService.removeMovie(movieToRemove);
    }

    public void addMovie(Movie movieToAdd) throws Exception {
        movieService.addMovie(movieToAdd);
    }

    public void updateMovie(Movie movie) throws Exception, IOException {
        movieService.updateMovie(movie);
    }

    public <T> List<T> getAllMovieProperty(MovieProperty movieProperty) {
        List<T> allMovieProperty = new ArrayList<>();
        List<Movie> allMovies = null;
        try {
            allMovies = movieService.getAllMoviesFromDb();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Movie movie : allMovies) {
            switch (movieProperty) {
                case NAME:
                    allMovieProperty.add((T) movie.getMovieName());
                    break;
                case TIME:
                    allMovieProperty.add((T) movie.getMovieTime());
                    break;
                case IMAGE:
                    allMovieProperty.add((T) movie.getMovieImage());
                    break;
                case ACTOR:
                    allMovieProperty.add((T) movie.getMovieActors());
                    break;
                case SYNOPSIS:
                    allMovieProperty.add((T) movie.getMovieSynopsis());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported MovieProperty: " + movieProperty);
            }
        }
        return allMovieProperty;
    }
}
