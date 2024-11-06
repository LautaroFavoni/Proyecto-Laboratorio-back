package com.example.practicaClase.persintence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Tenant extends User {

    @OneToOne(mappedBy = "tenant")
    @JsonBackReference
    private Property property;

    @ManyToOne
    @JsonBackReference
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
