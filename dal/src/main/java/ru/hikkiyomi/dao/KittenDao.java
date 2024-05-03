package ru.hikkiyomi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hikkiyomi.model.Kitten;

@Repository
public interface KittenDao extends Dao<Kitten>, JpaRepository<Kitten, Integer> {
}
