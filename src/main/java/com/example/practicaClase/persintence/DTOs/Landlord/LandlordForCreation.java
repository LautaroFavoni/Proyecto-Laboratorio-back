package com.example.practicaClase.persintence.DTOs.Landlord;

import java.util.List;

public class LandlordForCreation {
    private String name;
    private String password;
    private String role;
    private Long ownerId;
    private List<Long> propertyIds;

    // Constructores
    public LandlordForCreation() {
    }

    public LandlordForCreation(String name, String password, String role, Long ownerId, List<Long> propertyIds) {
        this.name = name;
        this.password = password;
        this.role = "landlord";
        this.ownerId = ownerId;
        this.propertyIds = propertyIds;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<Long> getPropertyIds() {
        return propertyIds;
    }

    public void setPropertyIds(List<Long> propertyIds) {
        this.propertyIds = propertyIds;
    }
}
