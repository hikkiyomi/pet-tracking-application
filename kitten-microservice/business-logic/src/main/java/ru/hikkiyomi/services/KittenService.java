package ru.hikkiyomi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.repositories.KittenDao;
import ru.hikkiyomi.models.Kitten;

import java.util.List;
import java.util.Optional;

@Service
public class KittenService implements CommonCrudService<Kitten> {
    @Autowired
    private KittenDao dao;

    public KittenService(KittenDao dao) {
        this.dao = dao;
    }

    @Override
    public Optional<Kitten> findById(Long id) {
        return dao.findById(id);
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
