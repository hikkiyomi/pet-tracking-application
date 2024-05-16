package ru.hikkiyomi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.dtos.AuthResponse;
import ru.hikkiyomi.model.Role;
import ru.hikkiyomi.model.User;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse signUp(
            String username,
            String password,
            Role role
    ) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        userService.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse login(
            String username,
            String password
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetailsServiceImpl provider = new UserDetailsServiceImpl();
        UserDetails userDetails = provider.loadUserByUsername(username);

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
