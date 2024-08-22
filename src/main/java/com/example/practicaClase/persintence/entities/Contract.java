package com.example.practicaClase.persintence.entities;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Contract extends Event {

    private LocalDateTime endDate;

    // Constructors, Getters, Setters

    public Contract() {
    }

    public Contract(LocalDateTime date, Tenant tenant, Property property, Landlord landlord, LocalDateTime endDate) {
        super(date, tenant, property,landlord);
        this.endDate = endDate;
    }
    // Getters y Setters
    public LocalDateTime getEndDate() {
        return endDate;
    }

    // Setter sobrecargado
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    // Setter con conversión de String a LocalDateTime
    public void setEndDate(String endDate) {
        this.endDate = LocalDateTime.parse(endDate);
    }
}
