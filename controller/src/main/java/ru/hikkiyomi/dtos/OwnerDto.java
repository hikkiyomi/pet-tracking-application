package ru.hikkiyomi.dtos;

import lombok.Data;
import ru.hikkiyomi.model.Owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class OwnerDto {
    private String name;
    private Date birthDate;
    private List<String> kittens;

    public OwnerDto(Optional<Owner> owner) {
        if (owner.isEmpty()) {
            return;
        }

        this.name = owner.get().getName();
        this.birthDate = owner.get().getBirthDate();
        this.kittens = new ArrayList<>();

        owner.get().getKittens().forEach(kitten -> this.kittens.add(kitten.getName()));
    }
}
