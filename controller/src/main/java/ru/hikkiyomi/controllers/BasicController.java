package ru.hikkiyomi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BasicController<T> {
    ResponseEntity<HttpStatus> create(T obj);
    T getById(Long id);
    List<T> getAll();
    void update(Long id, T obj);
    void delete(Long id);
}
