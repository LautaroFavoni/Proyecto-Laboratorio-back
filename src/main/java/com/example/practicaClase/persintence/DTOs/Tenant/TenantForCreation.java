package com.example.practicaClase.persintence.DTOs.Tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class TenantForCreation {
    @Email(message = "El correo electrónico no tiene un formato válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String mail;
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String password;
    private String role;
    private Long propertyId;
    private Long ownerId;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    // Constructores
    public TenantForCreation() {
    }

    public TenantForCreation(String mail, String password, String role, String name, Long propertyId, Long ownerId) {
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.role = "tenant";
        this.propertyId = propertyId;
        this.ownerId = ownerId;
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
