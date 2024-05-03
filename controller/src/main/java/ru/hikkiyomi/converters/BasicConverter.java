package ru.hikkiyomi.converters;

public interface BasicConverter<T, U> {
    U convert(T t);
}
