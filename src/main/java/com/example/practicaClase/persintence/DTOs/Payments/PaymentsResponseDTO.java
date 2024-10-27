package com.example.practicaClase.persintence.DTOs.Payments;

import java.time.LocalDateTime;

public class PaymentsResponseDTO {
    private Long id; // Asumiendo que tienes un ID en Payments
    private LocalDateTime date;
    private Long tenantId;
    private Long propertyId;
    private Long landlordId;
    private Double amount;

    public PaymentsResponseDTO(Long id, LocalDateTime date, Long tenantId, Long propertyId, Long landlordId, Double amount) {
        this.id = id;
        this.date = date;
        this.tenantId = tenantId;
        this.propertyId = propertyId;
        this.landlordId = landlordId;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
