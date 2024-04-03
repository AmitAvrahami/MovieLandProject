package com.hit.dao;

import com.hit.dm.movie.Movie;
import com.hit.dm.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserFileImpl implements IDao<Integer , User>{
    private String m_file_path;
    private ObjectOutputStream m_objectOutputStream;
    private ObjectInputStream m_objectInputStream;

    public UserFileImpl(String file_path) {
        this.m_file_path = file_path;
        boolean isEmptyFile = isEmptyDb();
        try {
            if (isEmptyFile) {
                this.m_objectOutputStream = new ObjectOutputStream(new FileOutputStream(m_file_path));
                m_objectOutputStream.writeObject(new Movie());
                m_objectOutputStream.flush();
                closeObjectOutStream();
            } else {
                this.m_objectInputStream = new ObjectInputStream(new FileInputStream(m_file_path));
                closeObjectInputStream();
            }
        } catch (IOException e) {
            System.out.println("file not open");
        }
    }

    private void closeObjectInputStream() {
        if (m_objectInputStream != null) {
            try {
                m_objectInputStream.close();
            } catch (IOException e) {
                System.out.println("IO exception");
            }
        }
    }

    private void closeObjectOutStream() {
        if (m_objectOutputStream != null) {
            try {
                m_objectOutputStream.close();
            } catch (IOException e) {
                System.out.println("IO exception");
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
            System.out.println(eofe.getMessage());
        } finally {
            closeObjectInputStream();
        }
        return allUsers;
    }

    @Override
    public List<User> getElementsByCount(List<User> elementsList, int elementsCount) throws IllegalArgumentException, NullPointerException {
        List<User> allUserList = new ArrayList<>();
        int i = 0;
        try {
            while (i < elementsCount) {
                User userFromDb = (User) m_objectInputStream.readObject();
                allUserList.add(userFromDb);
                i++;
            }
            closeObjectInputStream();
        } catch (EOFException eof) {
            if (allUserList.size() < elementsCount) {
                System.out.println("there are only" + allUserList.size());
                closeObjectInputStream();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading movie from database", e);
        } finally {
            closeObjectInputStream();
        }
        return allUserList;
    }

    @Override
    public User getElementById(Integer elementId) throws NoSuchElementException, IOException, ClassNotFoundException {
        List<User> allUserList = getAll();
        for (User user : allUserList) {
            if (user.getUserId() == elementId) {
                return user;
            }
        }
        throw new NoSuchElementException("user with ID " + elementId + " not found");
    }

    @Override
    public void addElement(User user) throws IOException, Exception {
        try {
            m_objectOutputStream.writeObject(user);
            m_objectOutputStream.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            closeObjectOutStream();
        }
    }


    @Override
    public void deleteElement(User objectToDelete) throws IOException, Exception {
        List<User> allUsers = getAll();
        if (allUsers.contains(objectToDelete)) {
            allUsers.remove(objectToDelete);
            updateElementsInFile(allUsers,m_file_path);
            closeObjectOutStream();
        } else {
            throw new IllegalArgumentException("User not found: " + objectToDelete);
        }
    }

    @Override
    public void updateElement(User userToUpdate) throws IOException, Exception {
        List<User> allUsers = getAll();
        if (allUsers.contains(userToUpdate)) {
            updateElementsInFile(allUsers,m_file_path);
            closeObjectOutStream();
        } else {
            throw new IllegalArgumentException("user not found: " + userToUpdate);
        }
    }



    private boolean isEmptyDb() {
        File dbMovieFile = new File(m_file_path);
        return !dbMovieFile.exists() || dbMovieFile.length() == 0;
    }
}
