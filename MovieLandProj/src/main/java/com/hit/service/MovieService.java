package com.hit.service;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.dao.MovieFileImpl;
import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieRateRange;

import java.io.IOException;
import java.util.*;

public class MovieService implements IMovieService {

    private MovieFileImpl dao;
    private IAlgoStringMatching m_stringMatchingAlgorithm;
    private List<Movie> m_allMovies;

    public MovieService(IAlgoStringMatching m_stringMatchingAlgorithm,MovieFileImpl movieDao) {
        this.m_stringMatchingAlgorithm = m_stringMatchingAlgorithm;
        this.dao = new MovieFileImpl();
        try{
            this.m_allMovies = dao.getAll();

        } catch (IOException | ClassNotFoundException e) {
           System.out.println("doesnt get all movies from the dao");
        }
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
            if (!m_stringMatchingAlgorithm.search(movie.getMovieName(),keyword).isEmpty()) {
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


    @Override
    public void removeMovie(Movie movieToRemove) throws Exception {
        if (!m_allMovies.contains(movieToRemove)) {
            throw new Exception("Movie not found: " + movieToRemove);
        }

        try {
            dao.deleteElement(movieToRemove);
            m_allMovies.remove(movieToRemove);
            Collections.sort(m_allMovies);
        } catch (Exception e) {
            // Handle any exceptions thrown by dao.deleteElement(movieToRemove)
            // For example, you can log the exception or rethrow it if necessary
            throw new Exception("Failed to remove movie: " + e.getMessage(), e);
        }
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
    public void updateMovie(Movie movie) throws Exception,IOException{
        boolean found = false;
        for (Movie movie1 : m_allMovies) {
            if (movie1.getMovieId().equals(movie.getMovieId())) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Movie with ID " + movie.getMovieId() + " does not exist.");
        }
        try {
            dao.updateElement(movie);
            m_allMovies.removeIf(movie1 -> movie1.getMovieId().equals(movie.getMovieId()));
            m_allMovies.add(movie);
            Collections.sort(m_allMovies);
        } catch (Exception e) {
            throw new IOException("Failed to update movie: " + e.getMessage(), e);
        }
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
    public List<Movie> getMoviesByActorFullName(String name) {
        List<Movie> moviesByActorName = new ArrayList<>();
        try {
            for (Movie movie : m_allMovies) {
                for (Actor actor : movie.getMovieActors()) {
                    if (!m_stringMatchingAlgorithm.search(actor.getActorName() + actor.getActorLastName(), name).isEmpty()) {
                        moviesByActorName.add(movie);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
        return moviesByActorName;
    }


}
