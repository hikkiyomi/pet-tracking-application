package ru.hikkiyomi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.dao.OwnerDao;
import ru.hikkiyomi.model.Owner;

import java.util.List;

@Service
public class OwnerService implements CommonService<Owner> {
    @Autowired
    private OwnerDao dao;

    public OwnerService(OwnerDao dao) {
        this.dao = dao;
    }

    @Override
    public Owner findById(int id) {
        return (Owner) dao.findById(id);
    }

    @Override
    public void save(Owner obj) {
        dao.save(obj);
    }

    @Override
    public void delete(Owner obj) {
        dao.delete(obj);
    }

    @Override
    public List<Owner> findAll() {
        return dao.findAll();
    }
}
