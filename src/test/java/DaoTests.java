import org.junit.jupiter.api.*;
import ru.hikkiyomi.dao.KittenDao;
import ru.hikkiyomi.dao.OwnerDao;
import ru.hikkiyomi.model.Breed;
import ru.hikkiyomi.model.Color;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;
import ru.hikkiyomi.service.KittenService;
import ru.hikkiyomi.service.OwnerService;
import ru.hikkiyomi.service.CommonService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class DaoTests {
    @Test
    public void ShouldBeFetchedRightOwner() {
        OwnerDao ownerDao = mock(OwnerDao.class);
        CommonService<Owner> service = new OwnerService(ownerDao);

        when(ownerDao.findById(1)).thenReturn(
                new Owner(
                        "ok",
                        Date.valueOf(LocalDate.of(1986, 4, 26))
                )
        );

        assertEquals("ok", service.findById(1).getName());
    }

    @Test
    public void ShouldFetchRightCatOfSomeOwner() {
        OwnerDao ownerDao = mock(OwnerDao.class);
        CommonService<Owner> service = new OwnerService(ownerDao);
        Owner owner = new Owner("me", Date.valueOf(LocalDate.of(2019, 1, 1)));

        owner.addKitten(
                new Kitten(
                    "balbes",
                    Date.valueOf(LocalDate.of(2019, 2, 2)),
                    Breed.MAINE_COON,
                    Color.GREY
                )
        );

        when(ownerDao.findAll()).thenReturn(List.of(owner));

        assertEquals(Breed.MAINE_COON, service.findAll().get(0).getKittens().get(0).getBreed());
    }

    @Test
    public void ShouldBeFetchedRightKitten() {
        KittenDao kittenDao = mock(KittenDao.class);
        CommonService<Kitten> service = new KittenService(kittenDao);

        when(kittenDao.findById(1)).thenReturn(
                new Kitten(
                        "aaaaaaaaaaa",
                        Date.valueOf(LocalDate.of(1234, 1, 1)),
                        Breed.BURMESE,
                        Color.GREEN
                )
        );

        assertEquals("aaaaaaaaaaa", service.findById(1).getName());
        assertEquals(Color.GREEN, service.findById(1).getColor());
    }

    @Test
    public void KittenShouldHaveRightOwner() {
        KittenDao kittenDao = mock(KittenDao.class);
        CommonService<Kitten> service = new KittenService(kittenDao);
        Owner someOwner = new Owner("yeah", Date.valueOf(LocalDate.of(2900, 1, 1)));

        Kitten someKitten = new Kitten(
                "kek",
                Date.valueOf(LocalDate.of(123123123, 1, 1)),
                Breed.BRITISH,
                Color.BLACK
        );

        someOwner.addKitten(someKitten);

        when(kittenDao.findAll()).thenReturn(List.of(someKitten));

        assertEquals(someOwner, service.findAll().get(0).getOwner());
    }
}
