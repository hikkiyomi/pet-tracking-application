package ru.hikkiyomi.mappers;

import ru.hikkiyomi.dtos.KittenDto;
import ru.hikkiyomi.model.Kitten;

import java.util.ArrayList;

public class KittenDtoToKittenMapper implements BasicMapper<KittenDto, Kitten> {
    @Override
    public Kitten map(KittenDto kittenDto) {
        Kitten kitten = new Kitten();

        kitten.setName(kittenDto.getName());
        kitten.setBirthdate(kittenDto.getBirthdate());
        kitten.setBreed(kittenDto.getBreed());
        kitten.setColor(kittenDto.getColor());
        kitten.setFriends(new ArrayList<>());

        return kitten;
    }
}
