package com.hit.dao;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface IDao <ID extends java.io.Serializable,T> {

    List<T> getAll() throws IllegalArgumentException, IOException;
    List<T> getElementsByKeyWord(String query) throws Exception;
    List<T> getElementsByCount(List<T> elementsList ,int elementsCount) throws IllegalArgumentException,NullPointerException;
    T getElementById(ID elementId)throws NoSuchElementException,IOException;
    T getElementByKeyWord(String query)throws NoSuchElementException,IOException;
    void sortElementsBy(List<T> elementsList,String sortBy)throws NoSuchElementException,IOException;
    void addElement(T objectToAdd)throws IOException,Exception;
    void deleteElement(T objectToDelete)throws IOException,Exception;
    void saveElement(T objectToSave)throws IOException,Exception;
    void updateElement(T objectToUpdate)throws IOException,Exception;

}
