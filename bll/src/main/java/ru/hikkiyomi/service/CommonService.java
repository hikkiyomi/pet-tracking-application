package ru.hikkiyomi.service;

import java.util.List;
import java.util.Optional;

public interface CommonService<T> {
    Optional<T> findById(Long id);
    void save(T obj);
    void delete(T obj);
    List<T> findAll();
}
