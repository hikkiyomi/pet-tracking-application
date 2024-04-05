package ru.hikkiyomi.service;

import ru.hikkiyomi.dao.KittenDao;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;

import java.util.List;

public class KittenService implements Service<Kitten> {
    private final KittenDao dao = new KittenDao();

    @Override
    public Kitten findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void save(Kitten obj) {
        dao.save(obj);
    }

    @Override
    public void update(Kitten obj) {
        dao.update(obj);
    }

    @Override
    public void delete(Kitten obj) {
        dao.delete(obj);
    }

    @Override
    public List<Kitten> findAll() {
        return dao.findAll();
    }

    public Owner findOwnerById(int id) {
        return dao.findOwnerById(id);
    }
}
