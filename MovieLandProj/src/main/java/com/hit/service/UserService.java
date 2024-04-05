package com.hit.service;


import com.hit.dao.UserFileImpl;
import com.hit.dm.movie.Movie;
import com.hit.dm.user.PermissionLevel;
import com.hit.dm.user.User;

import java.io.IOException;
import java.util.*;

interface IUserService  {
    List<User>  getAllUsersFromDb() throws IOException, ClassNotFoundException;
    User getUserById(int userId) throws IOException, ClassNotFoundException;
    void logout(User user);
    void register(User user) throws Exception;
    void updateUser(User user) throws Exception;
    void addToWatchlist(User user, Movie movie) throws Exception;
    void removeFromWatchlist(User user, Movie movie) throws Exception;
    void updateWatchlistStatus(User user, Movie movie) throws Exception;
    void deleteUser(User user) throws Exception;
    boolean changePassword(User user, String newPassword) throws Exception;
    boolean login(String username, String password);
    boolean isAdmin(User user);

}

public class UserService implements IUserService {
    private UserFileImpl dao;
    private String m_filePath;



    private List<User> m_allUsers;

    public UserService(String filePath) throws IOException {
        this.m_filePath = filePath;
        this.dao = new UserFileImpl(filePath);
        try {
            this.m_allUsers = getAllUsersFromDb();
        } catch (ClassNotFoundException e) {
            System.out.println("doesnt get all users from the dao");
        }

    }

    public List<User> getAllUsers() {
       return m_allUsers;
    }
    @Override
    public List<User> getAllUsersFromDb() throws IOException, ClassNotFoundException {
        List<User> allUsers =  dao.getAll();
        m_allUsers = allUsers;
        return allUsers;
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
    public void updateUser(User user) throws Exception {
            dao.updateElement(user);

    }

    @Override
    public void addToWatchlist(User user, Movie movie) throws Exception {
        user.getUserMovieWatchList().put(movie,false);
        updateUser(user);
    }

    @Override
    public void removeFromWatchlist(User user, Movie movie) throws Exception {
        HashMap<Movie, Boolean> watchlist = user.getUserMovieWatchList();
        if (watchlist.containsKey(movie)) {
            watchlist.remove(movie);
            updateUser(user);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void updateWatchlistStatus(User user, Movie movie) throws Exception {
        HashMap<Movie, Boolean> watchlist = user.getUserMovieWatchList();
        if (watchlist.containsKey(movie)){
            Boolean status =  watchlist.get(movie);
            watchlist.put(movie,!status);
            updateUser(user);
        }
        else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteUser(User user) throws Exception {
        dao.deleteElement(user);

    }

    @Override
    public boolean changePassword(User user, String newPassword) throws Exception {
        if (user.getUserPassword()!=newPassword && !newPassword.isEmpty() && newPassword.length() > 3){
            user.setUserPassword(newPassword);
            updateUser(user);
            return true;
        }
        return false;
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
