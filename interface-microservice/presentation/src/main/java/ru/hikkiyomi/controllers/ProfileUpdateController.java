package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hikkiyomi.dtos.OwnerDto;
import ru.hikkiyomi.models.Owner;
import ru.hikkiyomi.kafka.producers.OwnerProducerService;
import ru.hikkiyomi.services.OwnerService;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileUpdateController {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerProducerService ownerProducerService;

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> updateBirthdate(@RequestBody OwnerDto ownerDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Owner> owner = ownerService.findByName(username);

        if (owner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (ownerDto.getName() != null) owner.get().setName(ownerDto.getName());
        if (ownerDto.getBirthdate() != null) owner.get().setBirthdate(ownerDto.getBirthdate());

        ownerProducerService.put(owner.get());

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
