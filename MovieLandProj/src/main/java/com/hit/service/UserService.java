package com.hit.service;


import com.hit.dao.IDao;
import com.hit.dao.UserFileImpl;
import com.hit.dm.movie.Movie;
import com.hit.dm.user.PermissionLevel;
import com.hit.dm.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface IUserService  {
    void getAllUsersFromDb() throws IOException, ClassNotFoundException;
    User getUserById(int userId) throws IOException, ClassNotFoundException;
    void logout(User user);
    void register(User user) throws Exception;
    void updateUser(User user);
    void addToWatchlist(User user, Movie movie);
    void removeFromWatchlist(User user, Movie movie);
    void updateWatchlistStatus(User user, Movie movie);
    void deleteUser(User user);
    void changePassword(User user, String newPassword);
    boolean login(String username, String password);
    boolean isAdmin(User user);

}

public class UserService implements IUserService {
    private UserFileImpl dao;
    private String m_filePath;
    private List<User> m_allUsers;

    public UserService(String filePath) {
        this.m_filePath = filePath;
        this.dao = new UserFileImpl(filePath);
    }

    @Override
    public void getAllUsersFromDb() throws IOException, ClassNotFoundException {
        m_allUsers = dao.getAll();
    }

    @Override
    public User getUserById(int userId) throws IOException, ClassNotFoundException {
        return dao.getElementById(userId);
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public void register(User user) throws Exception {
        dao.addElement(user);
        m_allUsers.add(user);
    }

    @Override
    public void updateUser(User user) {
        try{
            dao.updateElement(user);
        } catch (IOException e) {
            System.out.println("cant read io");
        } catch (Exception e) {
            System.out.println("cant read");
        }
    }

    @Override
    public void addToWatchlist(User user, Movie movie) {
        user.getUserMovieWatchList().put(movie,false);
        updateUser(user);
    }

    @Override
    public void removeFromWatchlist(User user, Movie movie) {
        HashMap<Movie, Boolean> watchlist = user.getUserMovieWatchList();
        if (watchlist.containsKey(movie)) {
            watchlist.remove(movie);
            updateUser(user);
        } else {
            System.out.println("Movie is not in the watchlist.");
        }
    }

    @Override
    public void updateWatchlistStatus(User user, Movie movie) {
        HashMap<Movie, Boolean> watchlist = user.getUserMovieWatchList();
        if (watchlist.containsKey(movie)){
            Boolean status =  watchlist.get(movie);
            watchlist.put(movie,!status);
            updateUser(user);
        }
        else{
            System.out.println("Movie is not in the watchlist.");
        }
    }

    @Override
    public void deleteUser(User user) {
        try{
            dao.deleteElement(user);
        } catch (IOException e) {
            System.out.println("cant read");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void changePassword(User user, String newPassword) {
        if (user.getUserPassword()!=newPassword && !newPassword.isEmpty() && newPassword.length() > 3){
            user.setUserPassword(newPassword);
            updateUser(user);
        }
        else System.out.println("cant change password");
    }

    @Override
    public boolean login(String username, String password) {
        for (User user : m_allUsers){
            if (user.getUserName() == username && user.getUserPassword() == password)
                return true;
        }
        return false;
    }

    @Override
    public boolean isAdmin(User user) {
        if (user.getPermissionLevel() == PermissionLevel.ADMIN){
            return true;
        }
        return false;
    }

}
