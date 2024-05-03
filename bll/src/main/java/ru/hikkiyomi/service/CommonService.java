package ru.hikkiyomi.service;

import java.util.List;

public interface CommonService<T> {
    T findById(int id);
    void save(T obj);
    void delete(T obj);
    List<T> findAll();
}
