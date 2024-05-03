package ru.hikkiyomi.converters;

import ru.hikkiyomi.dtos.OwnerDto;
import ru.hikkiyomi.model.Owner;

import java.util.ArrayList;

public class OwnerDtoToOwnerConverter implements BasicConverter<OwnerDto, Owner> {
    @Override
    public Owner convert(OwnerDto ownerDto) {
        Owner owner = new Owner();

        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        owner.setKittens(new ArrayList<>());

        return owner;
    }
}
