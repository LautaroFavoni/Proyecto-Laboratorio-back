package com.example.practicaClase.persintence.DTOs.Property;

public class PropertyForCreation {

    private String tenantMail;
    private String landlordMail;
    private String ownerMail;
    private String address;

    private String description;

    // Constructores
    public PropertyForCreation() {
    }

    public PropertyForCreation(String tenantMail, String landlordMail, String ownerMail, String address, String description  ) {
        this.tenantMail = tenantMail;
        this.landlordMail = landlordMail;
        this.ownerMail = ownerMail;
        this.address = address;
        this.description = description;
    }

    // Getters y Setters

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

    public String getTenantMail() {
        return tenantMail;
    }

    public void setTenantMail(String tenantMail) {
        this.tenantMail = tenantMail;
    }

    public String getLandlordMail() {
        return landlordMail;
    }

    public void setLandlordMail(String landlordMail) {
        this.landlordMail = landlordMail;
    }

    public String getOwnerMail() {
        return ownerMail;
    }

    public void setOwnerMail(String ownerMail) {
        this.ownerMail = ownerMail;
    }
}
