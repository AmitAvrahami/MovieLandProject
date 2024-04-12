package com.hit.dao;

import com.hit.dm.movie.Movie;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;

public interface IDao <ID extends java.io.Serializable,T> {

    List<T> getAll() throws IllegalArgumentException, IOException, ClassNotFoundException;
    T getElementById(ID elementId) throws NoSuchElementException, IOException, ClassNotFoundException;
     void addElement(T objectToAdd )throws IOException,Exception;
    void deleteElement(T objectToDelete)throws IOException,Exception;
    void updateElement(T objectToUpdate)throws IOException,Exception;
     default void updateElementsInFile(List<T> obectList, String filePath) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(obectList);
        }
    }

}
