package ru.hikkiyomi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hikkiyomi.model.Owner;

@Repository
public interface OwnerDao extends JpaRepository<Owner, Long> {
}
