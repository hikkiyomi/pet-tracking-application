package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hikkiyomi.mappers.OwnerDtoToOwnerMapper;
import ru.hikkiyomi.dtos.OwnerDto;
import ru.hikkiyomi.dtos.SimpleKitten;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;
import ru.hikkiyomi.service.KittenService;
import ru.hikkiyomi.service.OwnerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class OwnerController implements BasicController<OwnerDto> {
    @Autowired
    private OwnerService service;

    @Autowired
    private KittenService kittenService;

    private final OwnerDtoToOwnerMapper converter = new OwnerDtoToOwnerMapper();

    @Override
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody OwnerDto obj) {
        Owner owner = converter.map(obj);

        for (SimpleKitten sk : obj.getKittens()) {
            Optional<Kitten> potentialKitten = kittenService.findById(sk.id());
            potentialKitten.ifPresent(owner::addKitten);
        }

        service.save(owner);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @GetMapping("/get/{id}")
    public OwnerDto getById(@PathVariable Long id) {
        return new OwnerDto(service.findById(id));
    }

    @Override
    @GetMapping("/get")
    public List<OwnerDto> getAll() {
        List<Owner> owners = service.findAll();
        List<OwnerDto> dtos = new ArrayList<>();

        owners.forEach(owner -> dtos.add(new OwnerDto(Optional.of(owner))));

        return dtos;
    }

    @Override
    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody OwnerDto obj) {
        Optional<Owner> updating = service.findById(id);

        if (updating.isPresent()) {
            if (obj.getName() != null) updating.get().setName(obj.getName());
            if (obj.getBirthDate() != null) updating.get().setBirthDate(obj.getBirthDate());

            if (!obj.getKittens().isEmpty()) {
                List<Kitten> newKittens = new ArrayList<>();

                for (SimpleKitten sk : obj.getKittens()) {
                    Optional<Kitten> potentialKitten = kittenService.findById(sk.id());
                    potentialKitten.ifPresent(newKittens::add);
                }

                updating.get().setKittens(newKittens);
            }

            service.save(updating.get());
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Owner> deleting = service.findById(id);
        deleting.ifPresent(owner -> service.delete(owner));
    }
}
