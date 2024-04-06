package com.hit.dao;

import com.hit.dm.movie.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MovieFileImpl implements IDao<Integer, Movie> {

    //TODO dont forget to close file

    private final static String m_file_path = "MovieLandProj/src/main/resources/moviedatasource.txt" ;


    public MovieFileImpl() {
        if (isEmptyDb()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(m_file_path))) {
                objectOutputStream.writeObject(new ArrayList<Movie>());
            } catch (IOException e) {
                System.out.println("Failed to create new database file: " + e.getMessage());
            }
        }
    }




    @Override
    public List<Movie> getAll() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(m_file_path))) {
            return (List<Movie>) objectInputStream.readObject();
        }
    }


    public List<Movie> getElementsByCount(List<Movie> elementsList, int elementsCount) throws IOException {
        List<Movie> allMoviesList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(m_file_path))) {
            for (int i = 0; i < elementsCount; i++) {
                allMoviesList.add((Movie) objectInputStream.readObject());
            }
        } catch (EOFException | ClassNotFoundException eof) {
            if (allMoviesList.size() < elementsCount) {
                System.out.println("There are only " + allMoviesList.size() + " movies in the database.");
            }
        }
        return allMoviesList;
    }

    @Override
    public Movie getElementById(Integer elementId) throws IOException, ClassNotFoundException {
        List<Movie> allMovies = getAll();
        for (Movie movie : allMovies) {
            if (movie.getMovieId().equals(elementId)) {
                return movie;
            }
        }
        throw new NoSuchElementException("Movie with ID " + elementId + " not found");
    }

    @Override
    public void addElement(Movie movie) throws IOException, ClassNotFoundException {
        List<Movie> allMovies = getAll();
        allMovies.add(movie);
        updateElementsInFile(allMovies,m_file_path);
    }


    @Override
    public void deleteElement(Movie movieToDelete) throws IOException, ClassNotFoundException {
        List<Movie> allMovies = getAll();
        if (allMovies.remove(movieToDelete)) {
            updateElementsInFile(allMovies,m_file_path);
        } else {
            throw new IllegalArgumentException("Movie not found: " + movieToDelete);
        }
    }




    @Override
    public void updateElement(Movie movieToUpdate) throws IOException, ClassNotFoundException {
        List<Movie> allMovies = getAll();
        for (int i = 0; i < allMovies.size(); i++) {
            Movie movie = allMovies.get(i);
            if (movie.getMovieId().equals(movieToUpdate.getMovieId())) {
                allMovies.set(i, movieToUpdate);
                updateElementsInFile(allMovies ,m_file_path);
                return;
            }
        }
        throw new IllegalArgumentException("Movie not found: " + movieToUpdate);
    }


    private boolean isEmptyDb() {
        File dbMovieFile = new File(m_file_path);
        return !dbMovieFile.exists() || dbMovieFile.length() == 0;
    }
}
