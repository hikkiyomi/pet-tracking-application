package ru.hikkiyomi.service;

import ru.hikkiyomi.dao.OwnerDao;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;

import java.util.List;

public class OwnerService implements Service<Owner> {
    private final OwnerDao dao;

    public OwnerService() {
        this.dao = new OwnerDao();
    }

    public OwnerService(OwnerDao dao) {
        this.dao = dao;
    }

    @Override
    public Owner findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void save(Owner obj) {
        dao.save(obj);
    }

    @Override
    public void update(Owner obj) {
        dao.update(obj);
    }

    @Override
    public void delete(Owner obj) {
        dao.delete(obj);
    }

    @Override
    public List<Owner> findAll() {
        return dao.findAll();
    }

    public Kitten findKittenById(int id) {
        return dao.findKittenById(id);
    }

    public void removeKitten(Owner owner, Kitten kitten) {
        owner.removeKitten(kitten);
    }
}
