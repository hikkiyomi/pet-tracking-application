package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hikkiyomi.converters.KittenDtoToKittenConverter;
import ru.hikkiyomi.converters.OwnerDtoToOwnerConverter;
import ru.hikkiyomi.dtos.KittenDto;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.service.KittenService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kittens")
public class KittenController implements BasicController<KittenDto> {
    @Autowired
    private KittenService service;

    private KittenDtoToKittenConverter converter = new KittenDtoToKittenConverter();
    private OwnerDtoToOwnerConverter ownerConverter = new OwnerDtoToOwnerConverter();

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

        kittens.forEach(kitten -> kittenDtos.add(new KittenDto(Optional.of(kitten))));

        return kittenDtos;
    }

    @Override
    @PutMapping("/update/{id}")
    public void update(@PathVariable int id, @RequestBody KittenDto obj) {
        Optional<Kitten> updating = service.findById(id);

        if (updating.isPresent()) {
            updating.get().setName(obj.getName());
            updating.get().setBirthDate(obj.getBirthdate());
            updating.get().setBreed(obj.getBreed());
            updating.get().setColor(obj.getColor());
            updating.get().setOwner(ownerConverter.convert(obj.getOwner()));

            service.save(updating.get());
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        Optional<Kitten> deleting = service.findById(id);
        deleting.ifPresent(kitten -> service.delete(kitten));
    }
}
