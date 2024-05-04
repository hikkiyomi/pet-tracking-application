package ru.hikkiyomi.dtos;

import lombok.Data;
import ru.hikkiyomi.model.Breed;
import ru.hikkiyomi.model.Color;
import ru.hikkiyomi.model.Kitten;

import java.sql.Date;
import java.util.Optional;

@Data
public class KittenDto {
    private String name;
    private Date birthdate;
    private Breed breed;
    private Color color;
    private OwnerDto owner;

    public KittenDto(Optional<Kitten> kitten) {
        if (kitten.isEmpty()) {
            return;
        }

        this.name = kitten.get().getName();
        this.birthdate = kitten.get().getBirthDate();
        this.breed = kitten.get().getBreed();
        this.color = kitten.get().getColor();
        this.owner = new OwnerDto(Optional.ofNullable(kitten.get().getOwner()));
    }
}
