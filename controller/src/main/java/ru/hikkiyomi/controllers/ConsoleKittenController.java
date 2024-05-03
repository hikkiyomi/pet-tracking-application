package ru.hikkiyomi.controllers;

import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.service.KittenService;
import ru.hikkiyomi.service.CommonService;

import java.util.List;

public class ConsoleKittenController implements Controller<Kitten> {
    private final CommonService<Kitten> service;

    public ConsoleKittenController() {
        this.service = new KittenService();
    }

    public ConsoleKittenController(CommonService<Kitten> service) {
        this.service = service;
    }

    @Override
    public void findById(int id) {
        Kitten kitten = service.findById(id);
        System.out.println(kitten);
    }

    @Override
    public void save(Kitten obj) {
        service.save(obj);
        System.out.println("Saved " + obj);
    }

    @Override
    public void update(Kitten obj) {
        service.update(obj);
        System.out.println("Updated " + obj);
    }

    @Override
    public void delete(Kitten obj) {
        service.delete(obj);
        System.out.println("Deleted " + obj);
    }

    @Override
    public void findAll() {
        List<Kitten> result = service.findAll();
        System.out.println(result);
    }
}
