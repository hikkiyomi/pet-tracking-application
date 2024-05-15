package ru.hikkiyomi.dtos;

import lombok.Data;
import ru.hikkiyomi.model.Breed;
import ru.hikkiyomi.model.Color;
import ru.hikkiyomi.model.Kitten;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class KittenDto {
    private Long id;
    private String name;
    private Date birthdate;
    private Breed breed;
    private Color color;
    private SimpleOwner owner;
    private List<SimpleKitten> friends = new ArrayList<>();

    public KittenDto() {}

    public KittenDto(Optional<Kitten> kitten) {
        if (kitten.isEmpty()) {
            return;
        }

        this.id = kitten.get().getId();
        this.name = kitten.get().getName();
        this.birthdate = kitten.get().getBirthDate();
        this.breed = kitten.get().getBreed();
        this.color = kitten.get().getColor();

        if (kitten.get().getOwner() != null) {
            this.owner = new SimpleOwner(kitten.get().getOwner().getId(), kitten.get().getOwner().getName());
        }

        kitten.get().getFriends().forEach(k -> this.friends.add(new SimpleKitten(k.getId(), k.getName())));
    }
}
