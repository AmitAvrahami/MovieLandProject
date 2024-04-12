package org.example.movieclient.UI.MovieUploader;

import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.model.actor.Actor;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MovieUploaderController {

    private List<Actor> actorsList = new ArrayList<>();
    private Actor newActorToUpload;
    private Movie newMovieToUpload;
    private File destinationFile;
    private String imageToSave;
    private User user;

    private MainMovieScreenView previousScreen;

    public List<Actor> getActorsList() {
        return actorsList;
    }

    public void setActorsList(List<Actor> actorsList) {
        this.actorsList = actorsList;
    }

    public Actor getNewActorToUpload() {
        return newActorToUpload;
    }

    public void setNewActorToUpload(Actor newActorToUpload) {
        this.newActorToUpload = newActorToUpload;
    }

    public Movie getNewMovieToUpload() {
        return newMovieToUpload;
    }

    public void setNewMovieToUpload(Movie newMovieToUpload) {
        this.newMovieToUpload = newMovieToUpload;
    }

    public File getDestinationFile() {
        return destinationFile;
    }

    public void setDestinationFile(File destinationFile) {
        this.destinationFile = destinationFile;
    }

    public String getImageToSave() {
        return imageToSave;
    }

    public void setImageToSave(String imageToSave) {
        this.imageToSave = imageToSave;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MainMovieScreenView getPreviousScreen() {
        return previousScreen;
    }

    public void setPreviousScreen(MainMovieScreenView previousScreen) {
        this.previousScreen = previousScreen;
    }
}
