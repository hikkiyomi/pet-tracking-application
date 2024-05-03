package ru.hikkiyomi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.dao.KittenDao;
import ru.hikkiyomi.model.Kitten;

import java.util.List;

@Service
public class KittenService implements CommonService<Kitten> {
    @Autowired
    private KittenDao dao;

    public KittenService(KittenDao dao) {
        this.dao = dao;
    }

    @Override
    public Kitten findById(int id) {
        return (Kitten) dao.findById(id);
    }

    @Override
    public void save(Kitten obj) {
        dao.save(obj);
    }

    @Override
    public void delete(Kitten obj) {
        dao.delete(obj);
    }

    @Override
    public List<Kitten> findAll() {
        return dao.findAll();
    }
}
