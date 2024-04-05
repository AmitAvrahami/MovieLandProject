package com.hit.dao;

import com.hit.dm.movie.Movie;
import com.hit.dm.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserFileImpl implements IDao<Integer, User> {
    private String m_filePath;

    public UserFileImpl(String filePath) throws IOException {
        this.m_filePath = filePath;
        boolean isEmptyFile = isEmptyDb();
        if (isEmptyFile) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(m_filePath))) {
                objectOutputStream.writeObject(new ArrayList<User>());
            } catch (IOException e) {
                throw new IOException();
            }
        }
    }



    @Override
    public List<User> getAll() throws IllegalArgumentException, IOException, ClassNotFoundException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(m_filePath))) {
            return (List<User>) objectInputStream.readObject();
        }
    }


    @Override
    public User getElementById(Integer elementId) throws NoSuchElementException, IOException, ClassNotFoundException {
        List<User> allUser = getAll();
            for(User user : allUser){
                if (user.getUserId().equals(elementId))
                    return user;
            }
        throw new NoSuchElementException();
    }

    @Override
    public void addElement(User user) throws IOException, Exception {
        List<User> allUsers = getAll();
        allUsers.add(user);
        updateElementsInFile(allUsers,m_filePath);

    }

    @Override
    public void deleteElement(User userToDelete) throws IOException, Exception {
        List<User> allMovies = getAll();
        if (allMovies.remove(userToDelete)) {
            updateElementsInFile(allMovies,m_filePath);
        } else {
            throw new IllegalArgumentException("User not found: " + userToDelete.getUserName());
        }
    }

    @Override
    public void updateElement(User userToUpdate) throws IOException, Exception {
        List<User> allUsers = getAll();
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getUserId().equals(userToUpdate.getUserId())) {
                allUsers.set(i, userToUpdate);
                updateElementsInFile(allUsers ,m_filePath);
                return;
            }
        }
        throw new NoSuchElementException("User not found: " + userToUpdate.getUserName());
    }

    private boolean isEmptyDb() {
        File dbUserFile = new File(m_filePath);
        return !dbUserFile.exists() || dbUserFile.length() == 0;
    }
}
