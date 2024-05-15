package ru.hikkiyomi.dao;

public interface Dao<T> {
    T findById(Long id);
}
