package com.hit.service;

import com.hit.algorithm.RabinCarpStringMatchingImpl;
import com.hit.dm.DataSample;
import com.hit.dm.actor.Actor;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.movie.MovieRateRange;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MovieServiceTest extends TestCase {
    MovieService movieService = new MovieService(new RabinCarpStringMatchingImpl(), "MovieLandProj/src/main/resources/moviedatasource.txt");
    private final List<Movie> sampleMovies = DataSample.getSampleMovies();


    public void writeSampleData() {
        List<Movie> sampleMovies = DataSample.getSampleMovies();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("MovieLandProj/src/main/resources/moviedatasource.txt"))) {
            outputStream.writeObject(sampleMovies);
            System.out.println("Sample data has been written.");
        } catch (IOException e) {
            System.out.println("Error writing sample data: " + e.getMessage());
        }
    }

    public void removeSampleData() {
        File file = new File("MovieLandProj/src/main/resources/moviedatasource.txt");
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Write an empty list to the file
            oos.writeObject(new ArrayList<>());
            System.out.println("Sample data file has been cleared.");
        } catch (IOException e) {
            System.err.println("Failed to clear sample data file: " + e.getMessage());
        }
    }


    public void testAddMovie() {
        removeSampleData();
        try {
            for (Movie movie : sampleMovies){
                movieService.addMovie(movie);
            }
            List<Movie> dbMovies = movieService.getAllMoviesFromDb();
            assertEquals(sampleMovies.size(),dbMovies.size());
            for (int i=0;i<sampleMovies.size();i++){
                Movie sampleMovie = sampleMovies.get(i);
                Movie dbMovie = dbMovies.get(i);
                assertEquals(sampleMovie.getMovieId(),dbMovie.getMovieId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetAllMovies() {
        removeSampleData();
        testAddMovie();
        List<Movie> allMovies = movieService.getAllMovies();
        assertEquals(sampleMovies.size(),allMovies.size());
        assertTrue(areMovieListsEqual(allMovies,sampleMovies));
        removeSampleData();
    }

    private boolean areMovieListsEqual(List<Movie> list1, List<Movie> list2) {
        writeSampleData();
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            Movie movie1 = list1.get(i);
            Movie movie2 = list2.get(i);
            if (!movie1.equals(movie2)) {
                return false;
            }
        }
        return true;
    }

    public void testGetAllMoviesFromDb() {
        testAddMovie();
        try {
            List<Movie> allMovies = movieService.getAllMoviesFromDb();
            assertEquals(sampleMovies.size(), allMovies.size());
            assertTrue(areMovieListsEqual(sampleMovies,allMovies));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            removeSampleData();
        }
    }

    public void testSearchMoviesByGenre() {
        writeSampleData();
        List<Movie> allMoviesByGenre = movieService.searchMoviesByGenre(MovieCategory.ACTION);
        assertFalse("No movies found for genre " + MovieCategory.ACTION, allMoviesByGenre.isEmpty());
        for (Movie movie : allMoviesByGenre){
            assertEquals(MovieCategory.ACTION , movie.getMovieCategory());
        }
        removeSampleData();
    }

    public void testSearchMoviesByMovieName() {
        writeSampleData();
        String movieName = "Titanic";
        List<Movie> allMoviesByName = movieService.searchMoviesByMovieName(movieName);
        assertFalse("No movies found for movie name Titanic ", allMoviesByName.isEmpty());
        for (Movie movie : allMoviesByName) {
            assertEquals(movieName, movie.getMovieName());
        }
        removeSampleData();
    }

    public void testRandomSelectionOfMovieByCategory() {
        writeSampleData();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Movie randomMovie = movieService.randomSelectionOfMovieByCategory(MovieCategory.FAMILY);
        assertEquals(MovieCategory.FAMILY,randomMovie.getMovieCategory());
        assertEquals(randomMovie.getClass(),Movie.class);
        assertFalse(randomMovie.getMovieCategory() == MovieCategory.ACTION);
        removeSampleData();
    }

    public void testRateAMovie() {
        writeSampleData();
        try{
            movieService.rateAMovie(1, MovieRateRange.THREE);
            sampleMovies.get(0).getMovieRate().add(MovieRateRange.THREE);

            Movie dataMovie = movieService.getAllMovies().get(0);
            Movie sampleMovie  = sampleMovies.get(0);
            assertEquals(sampleMovie.getMovieRate().get(0),dataMovie.getMovieRate().get(0));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            removeSampleData();
        }
    }

    public void testRemoveMovie() {
        writeSampleData();
        Movie movieToRemove = sampleMovies.get(2);
        try {
            movieService.removeMovie(movieToRemove);
            assertEquals(4,movieService.getAllMovies().size());
            assertFalse(movieService.getAllMovies().contains(movieToRemove));
            movieService.getAllMoviesFromDb();
            assertFalse(movieService.getAllMovies().contains(movieToRemove));
        } catch (Exception e) {
            assertFalse(movieService.getAllMovies().contains(movieToRemove));

        }
        finally {
            removeSampleData();
        }
    }


    public void testUpdateMovie() {
        writeSampleData();
        Movie movieToUpdate = sampleMovies.get(4);
        movieToUpdate.setMovieName("Harry Potter");
        try {
            movieService.updateMovie(movieToUpdate);
            assertEquals(movieToUpdate,movieService.getAllMovies().get(4));
        } catch (Exception e) {
            assertFalse(movieService.getAllMovies().contains(movieToUpdate));
        }finally {
            removeSampleData();
        }

    }

    public void testGetActorsByMovie() {
        writeSampleData();
        Movie sampleMovie = sampleMovies.get(0);
        List<Actor> actorsByMovie = movieService.getActorsByMovie(sampleMovie);
        List<Actor> actorsFromMovie = sampleMovie.getMovieActors();
        assertEquals("Number of actors returned should match", actorsFromMovie.size(), actorsByMovie.size());
        for (int i = 0; i < actorsByMovie.size(); i++) {
            Actor actorFromMovie = actorsFromMovie.get(i);
            Actor actorByMovie = actorsByMovie.get(i);
            assertEquals("Actor at index " + i + " should match", actorFromMovie, actorByMovie);
        }
        removeSampleData();
    }

    public void testGetMoviesByActorFullName() {
        writeSampleData();
        String john = "John";
        String Doe = "Doe";
        try {
            List<Movie> moviesByActor = movieService.getMoviesByActorFullName(john,Doe);
            assertEquals("Number of movies returned should match", 1, moviesByActor.size());
            assertEquals("The Avengers",moviesByActor.get(0).getMovieName());
        } catch (Exception e) {
            movieService.getAllMovies().forEach(movie -> {
                assertFalse(movie.getMovieActors().contains(john+Doe));
            });
        }finally {
            removeSampleData();
        }

    }
}