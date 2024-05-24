package ru.hikkiyomi.mappers;

import ru.hikkiyomi.dtos.OwnerDto;
import ru.hikkiyomi.models.Owner;

import java.util.ArrayList;

public class OwnerDtoToOwnerMapper implements BasicMapper<OwnerDto, Owner> {
    @Override
    public Owner map(OwnerDto ownerDto) {
        Owner owner = new Owner();

        owner.setName(ownerDto.getName());
        owner.setBirthdate(ownerDto.getBirthdate());
        owner.setKittens(new ArrayList<>());

        return owner;
    }
}
