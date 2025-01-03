package com.example.practicaClase.persintence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Landlord extends User {

    @OneToMany(mappedBy = "landlord")
    @JsonManagedReference
    private List<Property> propertyList;


    @ManyToOne
    @JsonBackReference
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


