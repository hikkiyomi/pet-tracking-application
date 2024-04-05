package ru.hikkiyomi.dao;

import ru.hikkiyomi.dao.methods.*;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;

import java.util.List;

public class KittenDao implements Dao<Kitten> {
    @Override
    public Kitten findById(int id) {
       return (new FindKittenMethod(id)).execute();
    }

    @Override
    public Void save(Kitten obj) {
        return (new SaveMethod<Kitten>(obj)).execute();
    }

    @Override
    public Void update(Kitten obj) {
        return (new UpdateMethod<Kitten>(obj)).execute();
    }

    @Override
    public Void delete(Kitten obj) {
        return (new DeleteMethod<Kitten>(obj)).execute();
    }

    @Override
    public List<Kitten> findAll() {
        return (new FetchKittenMethod()).execute();
    }

    public Owner findOwnerById(int id) {
        return (new FindOwnerMethod(id)).execute();
    }
}
