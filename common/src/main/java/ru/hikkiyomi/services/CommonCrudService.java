package ru.hikkiyomi.services;

import java.util.List;
import java.util.Optional;

public interface CommonCrudService<T> {
    Optional<T> findById(Long id);
    void save(T obj);
    void delete(T obj);
    List<T> findAll();
}
