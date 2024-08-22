package com.example.practicaClase.persintence.DTOs.Tenant;

public class TenantForCreation {
    private String name;
    private String password;
    private String role;
    private Long propertyId;
    private Long ownerId;

    // Constructores
    public TenantForCreation() {
    }

    public TenantForCreation(String name, String password, String role, Long propertyId, Long ownerId) {
        this.name = name;
        this.password = password;
        this.role = "tenant";
        this.propertyId = propertyId;
        this.ownerId = ownerId;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
