package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.hikkiyomi.mappers.KittenDtoToKittenMapper;
import ru.hikkiyomi.dtos.KittenDto;
import ru.hikkiyomi.dtos.SimpleKitten;
import ru.hikkiyomi.models.Kitten;
import ru.hikkiyomi.models.Owner;
import ru.hikkiyomi.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kittens")
public class KittenController implements BasicController<KittenDto> {
    @Autowired
    private KittenProducerService kittenProducerService;

    @Autowired
    private KittenConsumerService kittenConsumerService;

    @Autowired
    private OwnerConsumerService ownerConsumerService;

    @Autowired
    private AccessCheckService accessCheckService;

    private final KittenDtoToKittenMapper converter = new KittenDtoToKittenMapper();

    private String getPrincipalName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private ResponseEntity<?> checkIfAccessGranted(Long id) {
        Optional<Kitten> kitten = kittenConsumerService.findById(id);
        String username = getPrincipalName();

        if (kitten.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseEntity<?> response = accessCheckService.hasAccess(username, kitten.get());

        if (response.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            return response;
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody KittenDto obj) {
        try {
            Kitten kitten = converter.map(obj);
            Optional<Owner> owner = ownerConsumerService.findByName(getPrincipalName());

            for (SimpleKitten sk : obj.getFriends()) {
                Kitten friend = kittenConsumerService.findById(sk.id()).get();
                kitten.addFriend(friend);
            }

            kitten.setOwner(owner.get());
            kittenProducerService.post(kitten);

            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            ResponseEntity<?> response = checkIfAccessGranted(id);

            if (response.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return response;
            }

            return new ResponseEntity<>(new KittenDto(kittenConsumerService.findById(id)), HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        try {
            List<Kitten> kittens = kittenConsumerService.findAll();
            List<KittenDto> kittenDtos = new ArrayList<>();

            for (Kitten kitten : kittens) {
                ResponseEntity<?> response = checkIfAccessGranted(kitten.getId());

                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    kittenDtos.add(new KittenDto(Optional.of(kitten)));
                }
            }

            return new ResponseEntity<>(kittenDtos, HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id, @RequestBody KittenDto obj) {
        try {
            Optional<Kitten> updating = kittenConsumerService.findById(id);

            if (updating.isPresent()) {
                if (obj.getName() != null) updating.get().setName(obj.getName());
                if (obj.getBirthdate() != null) updating.get().setBirthdate(obj.getBirthdate());
                if (obj.getBreed() != null) updating.get().setBreed(obj.getBreed());
                if (obj.getColor() != null) updating.get().setColor(obj.getColor());

                if (obj.getOwner() != null) {
                    Optional<Owner> potentialOwner = ownerConsumerService.findById(obj.getOwner().id());
                    potentialOwner.ifPresent(owner -> updating.get().setOwner(owner));
                }

                kittenProducerService.put(updating.get());
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
            Optional<Kitten> deleting = kittenConsumerService.findById(id);
            deleting.ifPresent(kitten -> kittenProducerService.delete(kitten));

            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
