package ru.hikkiyomi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hikkiyomi.model.Owner;

public interface OwnerDao extends Dao<Owner>, JpaRepository<Owner, Integer> {
}
