package ru.hikkiyomi.converters;

import ru.hikkiyomi.dtos.KittenDto;
import ru.hikkiyomi.model.Kitten;

import java.util.ArrayList;

public class KittenDtoToKittenConverter implements BasicConverter<KittenDto, Kitten> {
    @Override
    public Kitten convert(KittenDto kittenDto) {
        Kitten kitten = new Kitten();

        kitten.setName(kittenDto.getName());
        kitten.setBirthDate(kittenDto.getBirthdate());
        kitten.setBreed(kittenDto.getBreed());
        kitten.setColor(kittenDto.getColor());
        kitten.setOwner(kittenDto.getOwner());
        kitten.setFriends(new ArrayList<>());

        return kitten;
    }
}
