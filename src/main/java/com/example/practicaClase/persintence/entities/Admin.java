package com.example.practicaClase.persintence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Admin extends User {

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<Owner> ownerList;

    // Getters and Setters
    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }
}
