package ru.hikkiyomi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hikkiyomi.models.Owner;

import java.util.Optional;

@Repository
public interface OwnerDao extends JpaRepository<Owner, Long> {
    Optional<Owner> findByName(String name);
}
