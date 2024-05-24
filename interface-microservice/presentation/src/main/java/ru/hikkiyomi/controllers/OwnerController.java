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
import ru.hikkiyomi.services.KittenConsumerService;
import ru.hikkiyomi.services.OwnerConsumerService;
import ru.hikkiyomi.kafka.producers.OwnerProducerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class OwnerController implements BasicController<OwnerDto> {
    @Autowired
    private OwnerProducerService ownerProducerService;

    @Autowired
    private OwnerConsumerService ownerConsumerService;

    @Autowired
    private KittenConsumerService kittenConsumerService;

    private final OwnerDtoToOwnerMapper converter = new OwnerDtoToOwnerMapper();

    @Override
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody OwnerDto obj) {
        try {
            Owner owner = converter.map(obj);

            for (SimpleKitten sk : obj.getKittens()) {
                Optional<Kitten> potentialKitten = kittenConsumerService.findById(sk.id());
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
            return new ResponseEntity<>(new OwnerDto(ownerConsumerService.findById(id)), HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        try {
            List<Owner> owners = ownerConsumerService.findAll();
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
            Optional<Owner> updating = ownerConsumerService.findById(id);

            if (updating.isPresent()) {
                if (obj.getName() != null) updating.get().setName(obj.getName());
                if (obj.getBirthdate() != null) updating.get().setBirthdate(obj.getBirthdate());

                if (!obj.getKittens().isEmpty()) {
                    List<Kitten> newKittens = new ArrayList<>();

                    for (SimpleKitten sk : obj.getKittens()) {
                        Optional<Kitten> potentialKitten = kittenConsumerService.findById(sk.id());
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
            Optional<Owner> deleting = ownerConsumerService.findById(id);
            deleting.ifPresent(owner -> ownerProducerService.delete(owner));

            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
