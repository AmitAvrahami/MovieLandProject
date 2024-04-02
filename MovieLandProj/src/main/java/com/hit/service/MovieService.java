package com.hit.service;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.algorithm.IAlgoStringMatchingTest;
import com.hit.dao.IDao;
import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieRateRange;

import java.util.List;

interface IMovieService{
    List<Movie> getAllMovies();
    List<Movie> searchMovies(String query);
    List<Movie> searchMoviesByGenre(MovieCategory movieCategory);
    List<Movie> searchMoviesByKeyword(String keyword);
    List<Actor> getActorsByMovieName(String movieName);
    List<Movie> getMoviesByActorFullName(String name , String lastName);
    Movie searchAMovie(String movieName);
    Movie randomSelectionOfMovieByCategory(MovieCategory movieCategory);
    void rateAMovie(Movie movieToRate , MovieRateRange movieRateRange);
    void removeMovie(Movie movieToRemove);
    void addMovie(Movie movieToAdd);
    void updateMovie(Movie movie);

}

public class MovieService implements IMovieService {

    private IDao dao;
    private IAlgoStringMatching m_stringMatchingAlgorithm;

    public MovieService(IAlgoStringMatching m_stringMatchingAlgorithm) {
        this.m_stringMatchingAlgorithm = m_stringMatchingAlgorithm;
    }

    public IAlgoStringMatching getStringMatchingAlgorithm() {
        return m_stringMatchingAlgorithm;
    }

    public void setStringMatchingAlgorithm(IAlgoStringMatching m_stringMatchingAlgorithm) {
        this.m_stringMatchingAlgorithm = m_stringMatchingAlgorithm;
    }

    private List<Integer> searchText(String text,String pattern){
        return m_stringMatchingAlgorithm.search(text,pattern);
    }

    @Override
    public List<Movie> getAllMovies() {
        return null;
    }

    @Override
    public List<Movie> searchMovies(String query) {
        return null;
    }

    @Override
    public List<Movie> searchMoviesByGenre(MovieCategory movieCategory) {
        return null;
    }



    @Override
    public List<Movie> searchMoviesByKeyword(String keyword) {
        return null;
    }

    @Override
    public Movie searchAMovie(String movieName) {
        return null;
    }

    @Override
    public Movie randomSelectionOfMovieByCategory(MovieCategory movieCategory) {
        return null;
    }

    @Override
    public void rateAMovie(Movie movieToRate, MovieRateRange movieRateRange) {

    }

    @Override
    public void removeMovie(Movie movieToRemove) {

    }

    @Override
    public void addMovie(Movie movieToAdd) {

    }

    @Override
    public void updateMovie(Movie movie) {

    }

    @Override
    public List<Actor> getActorsByMovieName(String movieName) {
        return null;
    }

    @Override
    public List<Movie> getMoviesByActorFullName(String name, String lastName) {
        return null;
    }

}
