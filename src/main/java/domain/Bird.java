package domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("Bird") // ustawienie nazwy typu w dziedziczeniu SINGLE_TABLE
//@PrimaryKeyJoinColumn(name = "animal_id") // ustawienie nazwy kolumny w dziedziczeniu JOINED
public class Bird extends Animal {

    private int wings;

    public Bird() {
    }

    public Bird(String name, int wings) {
        this.name = name;
        this.wings = wings;
    }
}
