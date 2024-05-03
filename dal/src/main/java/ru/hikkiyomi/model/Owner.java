package ru.hikkiyomi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "owners")
@Data
public class    Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kitten> kittens;

    public Owner() {}

    public Owner(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.kittens = new ArrayList<>();
    }

    public List<Kitten> getKittens() {
        return Collections.unmodifiableList(kittens);
    }

    public void addKitten(Kitten kitten) {
        if (kitten == null || kittens.contains(kitten)) {
            return;
        }

        kitten.setOwner(this);
        kittens.add(kitten);
    }

    public void removeKitten(Kitten kitten) {
        if (kitten == null || !kittens.contains(kitten)) {
            return;
        }

        kittens.remove(kitten);
    }
}
