package ru.hikkiyomi.dao;

import ru.hikkiyomi.dao.methods.*;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;

import java.util.List;

public class OwnerDao implements Dao<Owner> {
    @Override
    public Owner findById(int id) {
        return (new FindOwnerMethod(id)).execute();
    }

    @Override
    public Void save(Owner obj) {
        return (new SaveMethod<Owner>(obj)).execute();
    }

    @Override
    public Void update(Owner obj) {
        return (new UpdateMethod<Owner>(obj)).execute();
    }

    @Override
    public Void delete(Owner obj) {
        return (new DeleteMethod<Owner>(obj)).execute();
    }

    @Override
    public List<Owner> findAll() {
        return (new FetchOwnerMethod()).execute();
    }

    public Kitten findKittenById(int id) {
        return (new FindKittenMethod(id)).execute();
    }
}
