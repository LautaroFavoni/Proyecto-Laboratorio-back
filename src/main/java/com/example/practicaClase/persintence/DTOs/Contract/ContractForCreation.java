package com.example.practicaClase.persintence.DTOs.Contract;

import java.time.LocalDateTime;


public class ContractForCreation {

    private LocalDateTime date;
    private String tenantMail;
    private Long propertyId;
    private Long landlordId;
    private LocalDateTime endDate;

    // Constructors, Getters, Setters

    public ContractForCreation() {
    }

    public ContractForCreation(LocalDateTime date, String tenantMail, Long propertyId, Long landlordId, LocalDateTime endDate) {
        this.date = date;
        this.tenantMail = tenantMail;
        this.propertyId = propertyId;
        this.landlordId = landlordId;
        this.endDate = endDate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTenantMail() {
        return tenantMail;
    }

    public void setTenantMail(String tenantMail) {
        this.tenantMail = tenantMail;
    }
}
