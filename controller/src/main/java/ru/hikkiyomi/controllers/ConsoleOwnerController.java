package ru.hikkiyomi.controllers;

import ru.hikkiyomi.model.Owner;
import ru.hikkiyomi.service.OwnerService;
import ru.hikkiyomi.service.CommonService;

import java.util.List;

public class ConsoleOwnerController implements Controller<Owner> {
    private final CommonService<Owner> service;

    ConsoleOwnerController() {
        this.service = new OwnerService();
    }

    ConsoleOwnerController(CommonService<Owner> service) {
        this.service = service;
    }

    @Override
    public void findById(int id) {
        Owner owner = service.findById(id);
        System.out.println(owner);
    }

    @Override
    public void save(Owner obj) {
        service.save(obj);
        System.out.println("Saved " + obj);
    }

    @Override
    public void update(Owner obj) {
        service.update(obj);
        System.out.println("Updated " + obj);
    }

    @Override
    public void delete(Owner obj) {
        service.delete(obj);
        System.out.println("Deleted " + obj);
    }

    @Override
    public void findAll() {
        List<Owner> result = service.findAll();
        System.out.println(result);
    }
}
