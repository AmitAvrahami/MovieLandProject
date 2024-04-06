package com.hit.controller;

import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieRateRange;
import com.hit.service.IMovieService;
import com.hit.service.MovieService;

import java.io.IOException;
import java.util.List;

public class MovieController implements IMovieService {


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

    public List<Movie> getMoviesByActorFullName(String name, String lastName) throws Exception {
        return movieService.getMoviesByActorFullName(name, lastName);
    }

    public Movie randomSelectionOfMovieByCategory(MovieCategory movieCategory) {
        return movieService.randomSelectionOfMovieByCategory(movieCategory);
    }

    public List<Movie> getAllMoviesFromDb() throws IOException, ClassNotFoundException {
        return movieService.getAllMoviesFromDb();
    }

    public void rateAMovie(Integer movieId, MovieRateRange movieRateRange) throws IOException, ClassNotFoundException {
        movieService.rateAMovie(movieId, movieRateRange);
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
}
