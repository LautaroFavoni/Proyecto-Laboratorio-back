package com.example.practicaClase.persintence.DTOs.Property;

public class PropertyResponseDTO {
    private Long id;
    private String adress;
    private String description;
    private String tenantMail;
    private String landlordMail;
    private String ownerMail;

    public PropertyResponseDTO(Long id, String adress, String description, String tenantMail, String ownerMail, String landlordMail) {
        this.id = id;
        this.adress = adress;
        this.description = description;
        this.tenantMail = tenantMail;
        this.landlordMail = landlordMail;
        this.ownerMail = ownerMail;
    }

    public PropertyResponseDTO( ) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
