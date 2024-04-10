package com.hit.service;

import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieRateRange;

import java.io.IOException;
import java.util.List;

public interface IMovieService  {
    List<Movie> searchMoviesByGenre(MovieCategory movieCategory);

    List<Movie> searchMoviesByMovieName(String keyword);

    List<Actor> getActorsByMovie(Movie movie);


    Movie randomSelectionOfMovieByCategory(MovieCategory movieCategory);

    List<Movie> getAllMoviesFromDb() throws IOException, ClassNotFoundException;

    void rateAMovie(Integer movieId, MovieRateRange movieRateRange) throws IOException, ClassNotFoundException;

    void removeMovie(Movie movieToRemove) throws Exception;

    void addMovie(Movie movieToAdd) throws Exception;

    void updateMovie(Movie movie) throws Exception,IOException;


    List<Movie> getMoviesByActorFullName(String name);
}
