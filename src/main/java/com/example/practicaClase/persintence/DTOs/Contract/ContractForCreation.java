package com.example.practicaClase.persintence.DTOs.Contract;

import jakarta.persistence.Entity;


public class ContractForCreation {

    private String date;
    private Long tenantId;
    private Long propertyId;
    private Long landlordId;
    private String endDate;

    // Constructors, Getters, Setters

    public ContractForCreation() {
    }

    public ContractForCreation(String date, Long tenantId, Long propertyId, Long landlordId, String endDate) {
        this.date = date;
        this.tenantId = tenantId;
        this.propertyId = propertyId;
        this.landlordId = landlordId;
        this.endDate = endDate;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
