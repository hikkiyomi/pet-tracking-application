package ru.hikkiyomi.dao;

import java.util.List;

public interface Dao<T> {
    T findById(int id);
    Void save(T obj);
    Void update(T obj);
    Void delete(T obj);
    List<T> findAll();
}
