package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hikkiyomi.converters.KittenDtoToKittenConverter;
import ru.hikkiyomi.converters.OwnerDtoToOwnerConverter;
import ru.hikkiyomi.dtos.KittenDto;
import ru.hikkiyomi.dtos.SimpleKitten;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;
import ru.hikkiyomi.service.KittenService;
import ru.hikkiyomi.service.OwnerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kittens")
public class KittenController implements BasicController<KittenDto> {
    @Autowired
    private KittenService service;

    @Autowired
    private OwnerService ownerService;

    private KittenDtoToKittenConverter converter = new KittenDtoToKittenConverter();
    private final OwnerDtoToOwnerConverter ownerConverter = new OwnerDtoToOwnerConverter();

    @Override
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody KittenDto obj) {
        Kitten kitten = converter.convert(obj);
        Optional<Owner> potentialOwner = ownerService.findById(obj.getOwner().id());

        for (SimpleKitten sk : obj.getFriends()) {
            Kitten friend = service.findById(sk.id()).get();
            kitten.addFriend(friend);
        }

        potentialOwner.ifPresent(kitten::setOwner);
        service.save(kitten);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @GetMapping("/get/{id}")
    public KittenDto getById(@PathVariable Long id) {
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
    public void update(@PathVariable Long id, @RequestBody KittenDto obj) {
        Optional<Kitten> updating = service.findById(id);

        if (updating.isPresent()) {
            if (obj.getName() != null) updating.get().setName(obj.getName());
            if (obj.getBirthdate() != null) updating.get().setBirthDate(obj.getBirthdate());
            if (obj.getBreed() != null) updating.get().setBreed(obj.getBreed());
            if (obj.getColor() != null) updating.get().setColor(obj.getColor());

            if (obj.getOwner() != null) {
                Optional<Owner> potentialOwner = ownerService.findById(obj.getOwner().id());
                potentialOwner.ifPresent(owner -> updating.get().setOwner(owner));
            }

            service.save(updating.get());
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Kitten> deleting = service.findById(id);
        deleting.ifPresent(kitten -> service.delete(kitten));
    }
}
