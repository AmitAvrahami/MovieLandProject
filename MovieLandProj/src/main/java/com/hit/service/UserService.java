package com.hit.service;


import com.hit.dao.IDao;
import com.hit.dm.movie.Movie;
import com.hit.dm.user.User;

import java.util.List;

interface IUserService  {
    List<Movie> getWatchlist(User user);
    List<User> getAllUsers();
    User getUserById(int userId);
    void logout(User user);
    void register(User user);
    void updateUser(User user);
    void addToWatchlist(User user, Movie movie);
    void removeFromWatchlist(User user, Movie movie);
    void updateWatchlistStatus(User user, Movie movie, boolean watched);
    void deleteUser(User user);
    void changePassword(User user, String newPassword);
    boolean login(String username, String password);
    boolean isAdmin(User user);
    boolean isMovieInUserWatchlist(User user, Movie movie);
}

public class UserService implements IUserService {
    private IDao dao;

    @Override
    public List<Movie> getWatchlist(User user) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public void register(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void addToWatchlist(User user, Movie movie) {

    }

    @Override
    public void removeFromWatchlist(User user, Movie movie) {

    }

    @Override
    public void updateWatchlistStatus(User user, Movie movie, boolean watched) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void changePassword(User user, String newPassword) {

    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean isAdmin(User user) {
        return false;
    }

    @Override
    public boolean isMovieInUserWatchlist(User user, Movie movie) {
        return false;
    }
}
