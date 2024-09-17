package com.example.practicaClase.persintence.DTOs.Admin;

import java.util.List;

public class AdminForCreation {

    private String mail;
    private String password;
    private String role;
    private List<Long> ownerIds;

    // Constructores
    public AdminForCreation() {
    }

    public AdminForCreation(String mail, String password, String role, List<Long> ownerIds) {
        this.mail = mail;
        this.password = password;
        this.role = "admin";
        this.ownerIds = ownerIds;
    }

    // Getters y Setters
    public String getMail() {
        return mail;
    }

    public void setMail(String name) {
        this.mail = name;
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

    public List<Long> getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(List<Long> ownerIds) {
        this.ownerIds = ownerIds;
    }
}


