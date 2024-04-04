package com.hit.service;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.dao.MovieFileImpl;
import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieRateRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface IMovieService {
    List<Movie> searchMoviesByGenre(MovieCategory movieCategory);

    List<Movie> searchMoviesByMovieName(String keyword);

    List<Actor> getActorsByMovie(Movie movie);

    List<Movie> getMoviesByActorFullName(String name, String lastName);

    Movie randomSelectionOfMovieByCategory(MovieCategory movieCategory);

    List<Movie> getAllMoviesFromDb() throws IOException, ClassNotFoundException;

    void rateAMovie(Integer movieId, MovieRateRange movieRateRange) throws IOException, ClassNotFoundException;

    void removeMovie(Movie movieToRemove) throws Exception;

    void addMovie(Movie movieToAdd) throws Exception;

    void updateMovie(Movie movie) throws Exception;

}

public class MovieService implements IMovieService {

    private MovieFileImpl dao;
    private String m_filePath;
    private IAlgoStringMatching m_stringMatchingAlgorithm;
    private List<Movie> m_allMovies;

    public MovieService(IAlgoStringMatching m_stringMatchingAlgorithm, String filePath) {
        this.m_stringMatchingAlgorithm = m_stringMatchingAlgorithm;
        this.m_filePath = filePath;
        this.dao = new MovieFileImpl(filePath);
        this.m_allMovies = new ArrayList<>();
    }


    public IAlgoStringMatching getStringMatchingAlgorithm() {
        return m_stringMatchingAlgorithm;
    }

    public void setStringMatchingAlgorithm(IAlgoStringMatching m_stringMatchingAlgorithm) {
        this.m_stringMatchingAlgorithm = m_stringMatchingAlgorithm;
    }

    private List<Integer> searchText(String text, String pattern) {
        return m_stringMatchingAlgorithm.search(text, pattern);
    }

    public List<Movie> getAllMovies() {
        return m_allMovies;
    }

    @Override
    public List<Movie> getAllMoviesFromDb() throws IOException, ClassNotFoundException {
        m_allMovies = dao.getAll();
        return m_allMovies;
    }


    @Override
    public List<Movie> searchMoviesByGenre(MovieCategory movieCategory) {
        List<Movie> allMoviesByCategory = new ArrayList<>();
        for (Movie movie : m_allMovies) {
            if (movie.getMovieCategory() == movieCategory) {
                allMoviesByCategory.add(movie);
            }
        }
        return allMoviesByCategory;
    }


    @Override
    public List<Movie> searchMoviesByMovieName(String keyword) {
        List<Movie> allMovieByMovieName = new ArrayList<>();
        for (Movie movie : m_allMovies) {
            if (movie.getMovieName().contains(keyword)) {
                allMovieByMovieName.add(movie);
            }
        }
        return allMovieByMovieName;
    }


    @Override
    public Movie randomSelectionOfMovieByCategory(MovieCategory movieCategory) {
        List<Movie> allMoviesByCategory = searchMoviesByGenre(movieCategory);
        if (allMoviesByCategory.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(allMoviesByCategory.size());
        return allMoviesByCategory.get(randomIndex);
    }

    private List<Integer> convertMovieRateToInteger(List<MovieRateRange> movieRateRanges) {
        List<Integer> ratings = new ArrayList<>();
        for (MovieRateRange rateRange : movieRateRanges) {
            ratings.add(rateRange.getValue());
        }
        return ratings;
    }

    private Integer calculateAverageOfRate(Movie movie) {
        List<Integer> ratings = convertMovieRateToInteger(movie.getMovieRate());
        if (ratings.isEmpty()) {
            return 1;
        }
        int totalRating = 0;
        for (Integer rating : ratings) {
            totalRating += rating;
        }
        Integer average = totalRating / ratings.size();
        if (average == 0) return 1;
        else return average;
    }

    @Override
    public void rateAMovie(Integer movieId, MovieRateRange movieRateRange) throws IOException, ClassNotFoundException {
        Movie movieToRate = dao.getElementById(movieId);
        if (movieToRate != null) {
            movieToRate.getMovieRate().add(movieRateRange);
        } else {
            System.out.println("movie with id" + movieId + "not found");
        }
    }

    @Override
    public void removeMovie(Movie movieToRemove) throws Exception {
        dao.deleteElement(movieToRemove);
    }

    @Override
    public void addMovie(Movie movieToAdd) {
        try {
            dao.addElement(movieToAdd);
            m_allMovies.add(movieToAdd);
        } catch (IOException e) {
           System.out.println("can read io problem");
        } catch (Exception e) {
            System.out.println("stam exception");
        }
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        dao.updateElement(movie);
        m_allMovies.remove(movie);
    }

    @Override
    public List<Actor> getActorsByMovie(Movie actorsFromMovie) {
        List<Actor> actorsByMovieName = new ArrayList<>();
        if (!actorsFromMovie.getMovieActors().isEmpty()) {
            for (Actor actor : actorsFromMovie.getMovieActors()) {
                actorsByMovieName.add(actor);
            }
            return actorsByMovieName;
        } else return null;


    }

    @Override
    public List<Movie> getMoviesByActorFullName(String name, String lastName) {
        List<Movie> moviesByActorName = new ArrayList<>();
        for (Movie movie : m_allMovies) {
            for (Actor actor : movie.getMovieActors()) {
                if (actor.getActorName() == name && actor.getActorLastName() == lastName) {
                    moviesByActorName.add(movie);
                    break;
                }
            }
        }
        if (moviesByActorName.isEmpty())
            return null;
        else return moviesByActorName;
    }

}
