package com.hit.controller;

import com.hit.dm.movie.Movie;
import com.hit.dm.user.User;
import com.hit.service.IUserService;
import com.hit.service.UserService;

import java.io.IOException;
import java.util.List;

public class UserController implements IUserService {

    private UserService m_userService;

    public UserController(UserService userService) {
        this.m_userService = userService;
    }

    @Override
    public List<User> getAllUsersFromDb() throws IOException, ClassNotFoundException {
        return m_userService.getAllUsersFromDb();
    }

    @Override
    public User getUserById(int userId) throws IOException, ClassNotFoundException {
        return m_userService.getUserById(userId);
    }

    @Override
    public void logout(User user) {
        m_userService.logout(user);
    }

    @Override
    public void register(User user) throws Exception {
        m_userService.register(user);
    }

    @Override
    public void updateUser(User user) throws Exception {
        m_userService.updateUser(user);
    }

    @Override
    public void addToWatchlist(User user, Movie movie) throws Exception {
        m_userService.addToWatchlist(user, movie);
    }

    @Override
    public void removeFromWatchlist(User user, Movie movie) throws Exception {
        m_userService.removeFromWatchlist(user, movie);
    }

    @Override
    public void updateWatchlistStatus(User user, Movie movie) throws Exception {
        m_userService.updateWatchlistStatus(user, movie);
    }

    @Override
    public void deleteUser(User user) throws Exception {
        m_userService.deleteUser(user);
    }

    @Override
    public boolean changePassword(User user, String newPassword) throws Exception {
        return m_userService.changePassword(user, newPassword);
    }

    @Override
    public boolean login(String username, String password) {
        return m_userService.login(username, password);
    }

    @Override
    public boolean isAdmin(User user) {
        return m_userService.isAdmin(user);
    }

    //TODO : make some methods as the service

}
