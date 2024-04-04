package com.hit.dao;

import com.hit.dm.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserFileImpl implements IDao<Integer, User> {
    private String m_filePath;
    private ObjectOutputStream m_objectOutputStream;
    private ObjectInputStream m_objectInputStream;

    public UserFileImpl(String filePath) {
        this.m_filePath = filePath;
        boolean isEmptyFile = isEmptyDb();
        try {
            if (isEmptyFile) {
                this.m_objectOutputStream = new ObjectOutputStream(new FileOutputStream(m_filePath));
            } else {
                this.m_objectInputStream = new ObjectInputStream(new FileInputStream(m_filePath));
            }
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    private void closeObjectInputStream() {
        if (m_objectInputStream != null) {
            try {
                m_objectInputStream.close();
            } catch (IOException e) {
                System.out.println("Error closing input stream: " + e.getMessage());
            }
        }
    }

    private void closeObjectOutputStream() {
        if (m_objectOutputStream != null) {
            try {
                m_objectOutputStream.close();
            } catch (IOException e) {
                System.out.println("Error closing output stream: " + e.getMessage());
            }
        }
    }

    @Override
    public List<User> getAll() throws IllegalArgumentException, IOException, ClassNotFoundException {
        List<User> allUsers = new ArrayList<>();
        try {
            while (true) {
                User userFromDb = (User) m_objectInputStream.readObject();
                allUsers.add(userFromDb);
            }
        } catch (EOFException eofe) {
            // Reached end of file
        } finally {
            closeObjectInputStream();
        }
        return allUsers;
    }



    @Override
    public User getElementById(Integer elementId) throws NoSuchElementException, IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(m_filePath))) {
            User userFromDb;
            while (true) {
                userFromDb = (User) objectInputStream.readObject();
                if (userFromDb.getUserId() == elementId) {
                    return userFromDb;
                }
            }
        } catch (EOFException eofe) {
            // End of file reached, user not found
            throw new NoSuchElementException("User with ID " + elementId + " not found");
        }
    }

    @Override
    public void addElement(User user) throws IOException, Exception {
        try {
            m_objectOutputStream.writeObject(user);
            m_objectOutputStream.flush();
        } catch (IOException ioe) {
            System.out.println("Error writing user to file: " + ioe.getMessage());
        }finally {
            closeObjectOutputStream();
        }
    }

    @Override
    public void deleteElement(User objectToDelete) throws IOException, Exception {
        List<User> allUsers = getAll();
        if (allUsers.contains(objectToDelete)) {
            allUsers.remove(objectToDelete);
            updateElementsInFile(allUsers, m_filePath);
        } else {
            throw new IllegalArgumentException("User not found: " + objectToDelete);
        }
    }

    @Override
    public void updateElement(User userToUpdate) throws IOException, Exception {
        List<User> allUsers = getAll();
        if (allUsers.contains(userToUpdate)) {
            updateElementsInFile(allUsers, m_filePath);
        } else {
            throw new IllegalArgumentException("User not found: " + userToUpdate);
        }
    }

    private boolean isEmptyDb() {
        File dbUserFile = new File(m_filePath);
        return !dbUserFile.exists() || dbUserFile.length() == 0;
    }
}
