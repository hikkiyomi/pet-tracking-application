package ru.hikkiyomi;

import ru.hikkiyomi.model.Breed;
import ru.hikkiyomi.model.Color;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;
import ru.hikkiyomi.service.OwnerService;

import java.sql.Date;
import java.time.LocalDate;

public class Program {
    public static void main(String[] args) {
        OwnerService service = new OwnerService();
        Owner master = new Owner("Vasek", Date.valueOf(LocalDate.of(1980, 1, 1)));
        Kitten black = new Kitten("lol", Date.valueOf(LocalDate.of(1980, 1, 2)), Breed.MAINE_COON, Color.BLACK);
        Kitten green = new Kitten("radioactive", Date.valueOf(LocalDate.of(1986, 4, 26)), Breed.ABYSSINIAN, Color.GREEN);
        Kitten somebodyOnceToldMe = new Kitten("123123123", Date.valueOf(LocalDate.of(1999, 1, 1)), Breed.BRITISH, Color.GREY);

        service.save(master);
        master.addKitten(black);
        master.addKitten(green);
        master.addKitten(somebodyOnceToldMe);
        black.addFriend(green);
        green.addFriend(black);
        somebodyOnceToldMe.addFriend(green);
        somebodyOnceToldMe.addFriend(black);
        green.addFriend(somebodyOnceToldMe);
        black.addFriend(somebodyOnceToldMe);
        service.update(master);

        System.out.println(service.findKittenById(10));
    }
}
