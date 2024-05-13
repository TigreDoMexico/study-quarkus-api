package org.example.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Superhero extends PanacheEntity {
    public String name;
    public String identity;

    public Superhero() { }

    public Superhero(String name, String identity) {
        this.name = name;
        this.identity = identity;
    }
}
