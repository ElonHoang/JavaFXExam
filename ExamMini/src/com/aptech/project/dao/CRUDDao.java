/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.project.dao;

import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author Administrator
 */
public interface CRUDDao<T> {
    public ObservableList<T> getAll();
    public T add(T t);
    public boolean delete(T t);
    public boolean update(T t);
    public  ObservableList<T> searchByName(String txt);
    public boolean checkExists(T t);
    public Optional<T> getById(int id);
}
