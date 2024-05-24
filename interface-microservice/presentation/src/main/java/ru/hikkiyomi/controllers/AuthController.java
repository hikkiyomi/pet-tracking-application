package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hikkiyomi.dtos.AuthResponse;
import ru.hikkiyomi.dtos.UserDto;
import ru.hikkiyomi.models.Owner;
import ru.hikkiyomi.models.Role;
import ru.hikkiyomi.security.AuthService;
import ru.hikkiyomi.services.OwnerProducerService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private OwnerProducerService ownerProducerService;

    @PostMapping("/signup")
    public AuthResponse signUp(@RequestBody UserDto user) {
        return authService.signUp(user.getUsername(), user.getPassword(), Role.ADMIN);
    }

    @PostMapping("/create_user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        AuthResponse response = authService.signUp(user.getUsername(), user.getPassword(), Role.USER);

        ownerProducerService.post(new Owner(user.getUsername(), null));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserDto user) {
        return authService.login(user.getUsername(), user.getPassword());
    }
}
