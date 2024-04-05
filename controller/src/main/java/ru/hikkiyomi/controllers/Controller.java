package ru.hikkiyomi.controllers;

public interface Controller<T> {
    void findById(int id);
    void save(T obj);
    void update(T obj);
    void delete(T obj);
    void findAll();
}
