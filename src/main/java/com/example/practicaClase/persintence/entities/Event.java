package com.example.practicaClase.persintence.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lanlord_id")
    private Landlord landlord;

    // Constructors, Getters, Setters

    public Event() {
    }

    public Event(LocalDateTime date, Tenant tenant, Property property, Landlord landlord) {
        this.date = date;
        this.tenant = tenant;
        this.property = property;
        this.landlord = landlord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
