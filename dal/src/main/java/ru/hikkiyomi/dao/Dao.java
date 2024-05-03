package ru.hikkiyomi.dao;

public interface Dao<T> {
    T findById(int id);
}
