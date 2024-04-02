package com.hit.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

public class MovieFileImpl implements IDao {

    @Override
    public List getAll() throws IllegalArgumentException, IOException {
        return null;
    }

    @Override
    public List getElementsByKeyWord(String query) throws Exception {
        return null;
    }

    @Override
    public List getElementsByCount(List elementsList, int elementsCount) throws IllegalArgumentException, NullPointerException {
        return null;
    }

    @Override
    public Object getElementById(Serializable elementId) throws NoSuchElementException, IOException {
        return null;
    }

    @Override
    public Object getElementByKeyWord(String query) throws NoSuchElementException, IOException {
        return null;
    }

    @Override
    public void sortElementsBy(List elementsList, String sortBy) throws NoSuchElementException, IOException {

    }

    @Override
    public void addElement(Object objectToAdd) throws IOException, Exception {

    }

    @Override
    public void deleteElement(Object objectToDelete) throws IOException, Exception {

    }

    @Override
    public void saveElement(Object objectToSave) throws IOException, Exception {

    }

    @Override
    public void updateElement(Object objectToUpdate) throws IOException, Exception {

    }
}
