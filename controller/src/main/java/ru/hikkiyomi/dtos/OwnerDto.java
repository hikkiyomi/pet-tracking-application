package ru.hikkiyomi.dtos;

import lombok.Data;
import ru.hikkiyomi.model.Owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class OwnerDto {
    private String name;
    private Date birthDate;
    private List<String> kittens;

    public OwnerDto(Owner owner) {
        this.name = owner.getName();
        this.birthDate = owner.getBirthDate();
        this.kittens = new ArrayList<>();

        owner.getKittens().forEach(kitten -> this.kittens.add(kitten.getName()));
    }
}
