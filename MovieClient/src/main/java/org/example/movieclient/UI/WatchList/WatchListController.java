package org.example.movieclient.UI.WatchList;

import org.example.movieclient.UI.MainMovie.MainMovieScreenView;
import org.example.movieclient.model.user.User;

public class WatchListController {

    private User user;
    private MainMovieScreenView mainMovieScreenView;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MainMovieScreenView getMainMovieScreenView() {
        return mainMovieScreenView;
    }

    public void setMainMovieScreenView(MainMovieScreenView mainMovieScreenView) {
        this.mainMovieScreenView = mainMovieScreenView;
    }
}
