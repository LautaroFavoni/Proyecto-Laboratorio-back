package com.example.practicaClase.persintence.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Landlord extends User {

    @OneToMany(mappedBy = "landlord")
    private List<Property> propertyList;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // Getters and Setters
    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
