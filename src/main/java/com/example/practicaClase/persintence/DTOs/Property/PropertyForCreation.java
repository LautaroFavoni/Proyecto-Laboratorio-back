package com.example.practicaClase.persintence.DTOs.Property;

public class PropertyForCreation {

    private Long tenantId;
    private Long landlordId;
    private Long ownerId;

    private String address;

    private String description;

    // Constructores
    public PropertyForCreation() {
    }

    public PropertyForCreation(Long tenantId, Long landlordId, Long ownerId, String address, String description  ) {
        this.tenantId = tenantId;
        this.landlordId = landlordId;
        this.ownerId = ownerId;
        this.address = address;
        this.description = description;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
