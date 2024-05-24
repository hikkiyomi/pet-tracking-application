package ru.hikkiyomi.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "kittens")
@Data
public class Kitten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private Date birthdate;

    @Column(name = "breed")
    @Enumerated(value = EnumType.STRING)
    private Breed breed;

    @Column(name = "color")
    @Enumerated(value = EnumType.STRING)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private Owner owner;

    @ManyToMany(targetEntity = Kitten.class)
    @JoinTable(
            name = "kittens_friends",
            joinColumns = { @JoinColumn(name = "kitten_id") },
            inverseJoinColumns = { @JoinColumn(name = "friend_id") }
    )
    @ToString.Exclude
    private List<Kitten> friends;

    public Kitten() {}

    public Kitten(String name, Date birthdate, Breed breed, Color color) {
        this.name = name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.color = color;
        this.friends = new ArrayList<>();
    }

    public List<Kitten> getFriends() {
        return Collections.unmodifiableList(friends);
    }

    public void addFriend(Kitten kitten) {
        if (kitten == null || friends.contains(kitten)) {
            return;
        }

        friends.add(kitten);
        kitten.addFriend(this);
    }
}
