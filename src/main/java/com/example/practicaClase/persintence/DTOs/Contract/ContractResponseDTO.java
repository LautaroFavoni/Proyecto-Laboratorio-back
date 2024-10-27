package com.example.practicaClase.persintence.DTOs.Contract;

import java.time.LocalDateTime;

public class ContractResponseDTO {

    private Long id;
    private LocalDateTime date;
    private Long tenantId;
    private Long propertyId;
    private Long landlordId;
    private LocalDateTime endDate;

    // Constructores
    public ContractResponseDTO() {}

    public ContractResponseDTO(Long id, LocalDateTime date, Long tenantId, Long propertyId, Long landlordId, LocalDateTime endDate) {
        this.id = id;
        this.date = date;
        this.tenantId = tenantId;
        this.propertyId = propertyId;
        this.landlordId = landlordId;
        this.endDate = endDate;
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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
