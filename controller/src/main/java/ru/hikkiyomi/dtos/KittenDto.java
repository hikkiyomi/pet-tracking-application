package ru.hikkiyomi.dtos;

import lombok.Data;
import ru.hikkiyomi.model.Breed;
import ru.hikkiyomi.model.Color;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;

import java.sql.Date;

@Data
public class KittenDto {
    private String name;
    private Date birthdate;
    private Breed breed;
    private Color color;
    private Owner owner;

    public KittenDto(Kitten kitten) {
        this.name = kitten.getName();
        this.birthdate = kitten.getBirthDate();
        this.breed = kitten.getBreed();
        this.color = kitten.getColor();
        this.owner = kitten.getOwner();
    }
}
