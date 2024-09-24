package com.example.practicaClase.persintence.DTOs.Landlord;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class LandlordForCreation {
    @Email(message = "El correo electrónico no tiene un formato válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String mail;
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String password;
    private String role;
    private Long ownerId;
    private List<Long> propertyIds;

    // Constructores
    public LandlordForCreation() {
    }

    public LandlordForCreation(String mail, String password, String role, Long ownerId, List<Long> propertyIds) {
        this.mail = mail;
        this.password = password;
        this.role = "landlord";
        this.ownerId = ownerId;
        this.propertyIds = propertyIds;
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
