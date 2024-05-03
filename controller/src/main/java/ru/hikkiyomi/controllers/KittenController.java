package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hikkiyomi.converters.KittenDtoToKittenConverter;
import ru.hikkiyomi.dtos.KittenDto;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.service.KittenService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kittens")
public class KittenController implements BasicController<KittenDto> {
    @Autowired
    private KittenService service;

    private KittenDtoToKittenConverter converter = new KittenDtoToKittenConverter();

    @Override
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody KittenDto obj) {
        service.save(converter.convert(obj));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @GetMapping("/get/{id}")
    public KittenDto getById(@PathVariable int id) {
        return new KittenDto(service.findById(id));
    }

    @Override
    @GetMapping("/get")
    public List<KittenDto> getAll() {
        List<Kitten> kittens = service.findAll();
        List<KittenDto> kittenDtos = new ArrayList<>();

        kittens.forEach(kitten -> kittenDtos.add(new KittenDto(kitten)));

        return kittenDtos;
    }

    @Override
    @PutMapping("/update/{id}")
    public void update(@PathVariable int id, @RequestBody KittenDto obj) {
        Kitten updating = service.findById(id);

        if (updating != null) {
            updating.setName(obj.getName());
            updating.setBirthDate(obj.getBirthdate());
            updating.setBreed(obj.getBreed());
            updating.setColor(obj.getColor());
            updating.setOwner(obj.getOwner());

            service.save(updating);
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        service.delete(service.findById(id));
    }
}
