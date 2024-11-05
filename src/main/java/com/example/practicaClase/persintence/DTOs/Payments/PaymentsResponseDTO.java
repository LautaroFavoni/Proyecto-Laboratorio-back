package com.example.practicaClase.persintence.DTOs.Payments;

import java.time.LocalDateTime;

public class PaymentsResponseDTO {
    private Long id; // Asumiendo que tienes un ID en Payments
    private LocalDateTime date;
    private String tenantMail;
    private String propertyDescription;
    private String landlordMail;
    private Double amount;

    public PaymentsResponseDTO(Long id, LocalDateTime date, String tenantMail, String propertyDescription, String landlordMail, Double amount) {
        this.id = id;
        this.date = date;
        this.tenantMail = tenantMail;
        this.propertyDescription = propertyDescription;
        this.landlordMail = landlordMail;
        this.amount = amount;
    }

    // Getters y Setters
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



    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTenantMail() {
        return tenantMail;
    }

    public void setTenantMail(String tenantMail) {
        this.tenantMail = tenantMail;
    }



    public String getLandlordMail() {
        return landlordMail;
    }

    public void setLandlordMail(String landlordMail) {
        this.landlordMail = landlordMail;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }
}
