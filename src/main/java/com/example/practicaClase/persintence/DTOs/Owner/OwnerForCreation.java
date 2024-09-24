package com.example.practicaClase.persintence.DTOs.Owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class OwnerForCreation {

    @Email(message = "El correo electrónico no tiene un formato válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String mail;
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String password;
    private String role;
    private Long adminId;
    private List<Long> propertyIds;
    private List<Long> landlordIds;
    private List<Long> tenantIds;

    // Constructores
    public OwnerForCreation() {
    }

    public OwnerForCreation(String mail, String password, String role, Long adminId, List<Long> propertyIds, List<Long> landlordIds, List<Long> tenantIds) {
        this.mail = mail;
        this.password = password;
        this.role = "owner";
        this.adminId = adminId;
        this.propertyIds = propertyIds;
        this.landlordIds = landlordIds;
        this.tenantIds = tenantIds;
    }

    // Getters y Setters
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public List<Long> getPropertyIds() {
        return propertyIds;
    }

    public void setPropertyIds(List<Long> propertyIds) {
        this.propertyIds = propertyIds;
    }

    public List<Long> getLandlordIds() {
        return landlordIds;
    }

    public void setLandlordIds(List<Long> landlordIds) {
        this.landlordIds = landlordIds;
    }

    public List<Long> getTenantIds() {
        return tenantIds;
    }

    public void setTenantIds(List<Long> tenantIds) {
        this.tenantIds = tenantIds;
    }
}
