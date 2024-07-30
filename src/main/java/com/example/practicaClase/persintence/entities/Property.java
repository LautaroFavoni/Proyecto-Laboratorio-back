package com.example.practicaClase.persintence.entities;

import com.example.practicaClase.persintence.entities.Landlord;
import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.entities.Tenant;
import jakarta.persistence.*;


@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String type;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // Constructors, Getters, Setters

    public Property() {
    }

    public Property(String address, String type, Landlord landlord, Tenant tenant, Owner owner) {
        this.address = address;
        this.type = type;
        this.landlord = landlord;
        this.tenant = tenant;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
