package ru.hikkiyomi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.hikkiyomi.model.Breed;
import ru.hikkiyomi.model.Color;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;
import ru.hikkiyomi.service.KittenService;
import ru.hikkiyomi.service.OwnerService;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DaoTests {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private KittenService kittenService;

    @Test
    public void ShouldBeFetchedRightOwner() {
        ownerService.save(new Owner("ok", Date.valueOf(LocalDate.of(1986, 4, 26))));
        assertEquals("ok", ownerService.findById(1L).get().getName());
    }

    @Test
    public void ShouldFetchRightCatOfSomeOwner() {
        Owner owner = new Owner("me", Date.valueOf(LocalDate.of(2019, 1, 1)));

        Kitten kitten = new Kitten(
                "balbes",
                Date.valueOf(LocalDate.of(2019, 2, 2)),
                Breed.MAINE_COON,
                Color.GREY
        );

        owner.addKitten(kitten);
        ownerService.save(owner);
        kittenService.save(kitten);

        assertEquals(Breed.MAINE_COON, ownerService.findAll().get(1).getKittens().get(0).getBreed());
    }

    @Test
    public void ShouldBeFetchedRightKitten() {
        kittenService.save(new Kitten(
                "aaaaaaaaaaa",
                Date.valueOf(LocalDate.of(1234, 1, 1)),
                Breed.BURMESE,
                Color.GREEN
        ));

        assertEquals("aaaaaaaaaaa", kittenService.findById(1L).get().getName());
        assertEquals(Color.GREEN, kittenService.findById(1L).get().getColor());
    }

    @Test
    public void KittenShouldHaveRightOwner() {
        Owner someOwner = new Owner("yeah", Date.valueOf(LocalDate.of(2900, 1, 1)));

        Kitten someKitten = new Kitten(
                "kek",
                Date.valueOf(LocalDate.of(123123123, 1, 1)),
                Breed.BRITISH,
                Color.BLACK
        );

        someOwner.addKitten(someKitten);
        ownerService.save(someOwner);
        kittenService.save(someKitten);

        // Comparing with toString() method, because somehow these instances are not identical in their true form. wtf
        assertEquals(someOwner.toString(), kittenService.findAll().get(2).getOwner().toString());
    }
}
