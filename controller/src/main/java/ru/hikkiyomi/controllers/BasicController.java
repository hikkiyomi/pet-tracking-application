package ru.hikkiyomi.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BasicController<T> {
    ResponseEntity create(T obj);
    T getById(int id);
    List<T> getAll();
    void update(int id, T obj);
    void delete(int id);
}
