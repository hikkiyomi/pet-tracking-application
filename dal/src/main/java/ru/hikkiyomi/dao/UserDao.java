package ru.hikkiyomi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hikkiyomi.model.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
