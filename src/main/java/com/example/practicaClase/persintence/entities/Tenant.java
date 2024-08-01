package com.example.practicaClase.persintence.entities;

import jakarta.persistence.*;

@Entity
public class Tenant extends User {

    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // Getters and Setters
    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
