package ru.hikkiyomi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hikkiyomi.model.User;

public interface UserDao extends JpaRepository<User, Long> {
}
