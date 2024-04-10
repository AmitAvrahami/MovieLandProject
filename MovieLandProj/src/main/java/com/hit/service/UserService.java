package com.hit.service;


import com.hit.dao.UserFileImpl;
import com.hit.dm.movie.Movie;
import com.hit.dm.user.PermissionLevel;
import com.hit.dm.user.User;

import java.io.IOException;
import java.util.*;

public class UserService implements IUserService {
    private UserFileImpl dao;
    private List<User> m_allUsers;

    public UserService() throws IOException {
        this.dao = new UserFileImpl();
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
        if (m_allUsers.contains(user)) {
            dao.deleteElement(user);
            m_allUsers.remove(user);
        } else {
            throw new NoSuchElementException("User does not exist");
        }
    }



    @Override
    public User login(String username, String password) {
        for (User user : m_allUsers){
            if (user.getUserName().equals(username) && user.getUserPassword().equals(password))
                return user;
        }
        return null;
    }

    @Override
    public boolean isAdmin(User user) {
        if (user.getPermissionLevel() == PermissionLevel.ADMIN){
            return true;
        }
        return false;
    }

}
