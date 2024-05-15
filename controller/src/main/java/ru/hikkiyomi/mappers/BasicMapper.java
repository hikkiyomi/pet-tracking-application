package ru.hikkiyomi.mappers;

public interface BasicMapper<T, U> {
    U map(T t);
}
