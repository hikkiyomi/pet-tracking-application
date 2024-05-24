package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hikkiyomi.mappers.OwnerDtoToOwnerMapper;
import ru.hikkiyomi.dtos.OwnerDto;
import ru.hikkiyomi.dtos.SimpleKitten;
import ru.hikkiyomi.models.Kitten;
import ru.hikkiyomi.models.Owner;
import ru.hikkiyomi.services.KittenService;
import ru.hikkiyomi.kafka.producers.OwnerProducerService;
import ru.hikkiyomi.services.OwnerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class OwnerController implements BasicController<OwnerDto> {
    @Autowired
    private OwnerProducerService ownerProducerService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private KittenService kittenService;

    private final OwnerDtoToOwnerMapper converter = new OwnerDtoToOwnerMapper();

    @Override
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody OwnerDto obj) {
        try {
            Owner owner = converter.map(obj);

            for (SimpleKitten sk : obj.getKittens()) {
                Optional<Kitten> potentialKitten = kittenService.findById(sk.id());
                potentialKitten.ifPresent(owner::addKitten);
            }

            ownerProducerService.post(owner);

            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(new OwnerDto(ownerService.findById(id)), HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        try {
            List<Owner> owners = ownerService.findAll();
            List<OwnerDto> dtos = new ArrayList<>();

            owners.forEach(owner -> dtos.add(new OwnerDto(Optional.of(owner))));

            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id, @RequestBody OwnerDto obj) {
        try {
            Optional<Owner> updating = ownerService.findById(id);

            if (updating.isPresent()) {
                if (obj.getName() != null) updating.get().setName(obj.getName());
                if (obj.getBirthdate() != null) updating.get().setBirthdate(obj.getBirthdate());

                if (!obj.getKittens().isEmpty()) {
                    List<Kitten> newKittens = new ArrayList<>();

                    for (SimpleKitten sk : obj.getKittens()) {
                        Optional<Kitten> potentialKitten = kittenService.findById(sk.id());
                        potentialKitten.ifPresent(newKittens::add);
                    }

                    updating.get().setKittens(newKittens);
                }

                ownerProducerService.put(updating.get());
            }

            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        try {
            Optional<Owner> deleting = ownerService.findById(id);
            deleting.ifPresent(owner -> ownerProducerService.delete(owner));

            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
