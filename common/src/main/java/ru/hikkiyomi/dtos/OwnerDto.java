package ru.hikkiyomi.dtos;

import lombok.Data;
import ru.hikkiyomi.models.Owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class OwnerDto {
    private Long id;
    private String name;
    private Date birthdate;
    private List<SimpleKitten> kittens = new ArrayList<>();

    public OwnerDto() {}

    public OwnerDto(Optional<Owner> owner) {
        if (owner.isEmpty()) {
            return;
        }

        this.id = owner.get().getId();
        this.name = owner.get().getName();
        this.birthdate = owner.get().getBirthdate();

        owner.get().getKittens().forEach(kitten -> this.kittens.add(new SimpleKitten(kitten.getId(), kitten.getName())));
    }
}
