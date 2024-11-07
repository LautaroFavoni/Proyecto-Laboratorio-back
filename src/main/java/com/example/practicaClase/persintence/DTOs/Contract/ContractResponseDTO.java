package com.example.practicaClase.persintence.DTOs.Contract;

import java.time.LocalDateTime;

public class ContractResponseDTO {

    private Long id;
    private LocalDateTime date;

    private  String tenantMail;

    private String propertyDescription;
    private String landlordMail;

    private LocalDateTime endDate;

    // Constructores
    public ContractResponseDTO() {}

    public ContractResponseDTO(Long id, LocalDateTime date, String tenantMail, String propertyDescription, String landlordMail, LocalDateTime endDate) {
        this.id = id;
        this.date = date;
        this.tenantMail = tenantMail;
        this.endDate = endDate;
        this.landlordMail = landlordMail;
        this.propertyDescription = propertyDescription;
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

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getLandlordMail() {
        return landlordMail;
    }

    public void setLandlordMail(String landlordMail) {
        this.landlordMail = landlordMail;
    }
}
