package com.example.practicaClase.persintence.DTOs.Payments;

public class PaymentsForCreation {
    private String date; // Utiliza el formato que prefieras para la fecha
    private Long tenantId;
    private Long propertyId;
    private Long landlordId;
    private Double amount;

    // Constructors, Getters, Setters

    public PaymentsForCreation() {
    }

    public PaymentsForCreation(String date, Long tenantId, Long propertyId, Long landlordId, Double amount) {
        this.date = date;
        this.tenantId = tenantId;
        this.propertyId = propertyId;
        this.landlordId = landlordId;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
