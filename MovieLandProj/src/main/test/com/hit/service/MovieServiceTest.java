package com.hit.service;

import com.hit.algorithm.RabinCarpStringMatchingImpl;
import com.hit.dm.DataSample;
import com.hit.dm.movie.Movie;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class MovieServiceTest extends TestCase {
    MovieService movieService = new MovieService(new RabinCarpStringMatchingImpl(), "MovieLandProj/src/main/resources/moviedatasource.txt");
    private final List<Movie> sampleMovies = DataSample.getSampleMovies();

//    @Test
//    public void testGetStringMatchingAlgorithm() {
//    }
//
//    public void testSetStringMatchingAlgorithm() {
//    }

    @Test
    public void testAddMovie() {
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

    @Test
    public void testGetAllMovies() {

    }

    public void testGetAllMoviesFromDb() {
    }

    public void testSearchMoviesByGenre() {
    }

    public void testSearchMoviesByMovieName() {
    }

    public void testRandomSelectionOfMovieByCategory() {
    }

    public void testRateAMovie() {
    }

    public void testRemoveMovie() {
    }


    public void testUpdateMovie() {
    }

    public void testGetActorsByMovie() {
    }

    public void testGetMoviesByActorFullName() {
    }
}