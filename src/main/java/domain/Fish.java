package domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("Fish") // ustawienie nazwy typu w dziedziczeniu SINGLE_TABLE
//@PrimaryKeyJoinColumn(name = "animal_id") // ustawienie nazwy kolumny w dziedziczeniu JOINED
public class Fish extends Animal {

    private int fin;

    public Fish() {
    }

    public Fish(String name, int fin) {
        this.name = name;
        this.fin = fin;
    }
}
