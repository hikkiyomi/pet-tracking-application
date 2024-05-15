package ru.hikkiyomi.dtos;

import lombok.Data;
import ru.hikkiyomi.model.Owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class OwnerDto {
    private Long id;
    private String name;
    private Date birthDate;
    private List<SimpleKitten> kittens = new ArrayList<>();

    public OwnerDto() {}

    public OwnerDto(Optional<Owner> owner) {
        if (owner.isEmpty()) {
            return;
        }

        this.id = owner.get().getId();
        this.name = owner.get().getName();
        this.birthDate = owner.get().getBirthDate();

        owner.get().getKittens().forEach(kitten -> this.kittens.add(new SimpleKitten(kitten.getId(), kitten.getName())));
    }
}
