package org.example.movieclient.UI;

import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.User;

import java.util.List;

public interface DataRefreshListener {
    void refreshData(User user);

}
