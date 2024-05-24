package ru.hikkiyomi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BasicController<T> {
    ResponseEntity<HttpStatus> create(T obj);
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> getAll();
    ResponseEntity<HttpStatus> update(Long id, T obj);
    ResponseEntity<HttpStatus> delete(Long id);
}
