package com.example.practicaClase.persintence.DTOs.Property;

public class PropertyForCreation {

    private Long tenantId;
    private Long landlordId;
    private Long ownerId;

    // Constructores
    public PropertyForCreation() {
    }

    public PropertyForCreation(Long tenantId, Long landlordId, Long ownerId) {
        this.tenantId = tenantId;
        this.landlordId = landlordId;
        this.ownerId = ownerId;
    }

    // Getters y Setters
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
