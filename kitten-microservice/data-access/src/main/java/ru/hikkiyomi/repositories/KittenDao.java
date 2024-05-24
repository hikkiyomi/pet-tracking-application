package ru.hikkiyomi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hikkiyomi.models.Kitten;

@Repository
public interface KittenDao extends JpaRepository<Kitten, Long> {
}
