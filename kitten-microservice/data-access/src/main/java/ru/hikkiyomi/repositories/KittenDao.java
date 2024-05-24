package ru.hikkiyomi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hikkiyomi.models.Kitten;

public interface KittenDao extends JpaRepository<Kitten, Long> {
}
