package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hikkiyomi.dtos.AuthResponse;
import ru.hikkiyomi.dtos.UserDto;
import ru.hikkiyomi.model.Role;
import ru.hikkiyomi.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signUpAsUser(@RequestBody UserDto user) {
        return authService.signUp(user.getUsername(), user.getPassword(), Role.USER);
    }

    @PostMapping("/signupAdmin")
    public AuthResponse signUpAsAdmin(@RequestBody UserDto user) {
        return authService.signUp(user.getUsername(), user.getPassword(), Role.ADMIN);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserDto user) {
        return authService.login(user.getUsername(), user.getPassword());
    }
}
