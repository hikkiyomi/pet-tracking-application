package ru.hikkiyomi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.models.Kitten;
import ru.hikkiyomi.models.Owner;

import java.util.Optional;

@Service
public class AccessCheckService {
    @Autowired
    private KittenService kittenService;

    @Autowired
    private OwnerService ownerService;

    public ResponseEntity<?> hasAccess(String username, Kitten requestedKitten) {
        Optional<Owner> owner = ownerService.findByName(username);
        Optional<Kitten> kitten = kittenService.findById(requestedKitten.getId());

        if (owner.isEmpty() || kitten.isEmpty()) {
            return new ResponseEntity<>("No such owner or kitten found", HttpStatus.BAD_REQUEST);
        }

        if (owner.get().getId() != kitten.get().getOwner().getId()) {
            return new ResponseEntity<>("No access to such kitten", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
