package com.example.practicaClase.persintence.DTOs.Property;

public class PropertyResponseDTO {
    private Long id;
    private String adress;
    private String description;
    private Long tenantId;
    private Long landlordId;

    private String landordMail;
    private Long ownerId;

    public PropertyResponseDTO(Long id, String adress, String description, Long tenantId, Long landlordId, Long ownerId, String landordMail) {
        this.id = id;
        this.adress = adress;
        this.description = description;
        this.tenantId = tenantId;
        this.landlordId = landlordId;
        this.ownerId = ownerId;
        this.landordMail = landordMail;
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

    public String getLandordMail() {
        return landordMail;
    }

    public void setLandordMail(String landordMail) {
        this.landordMail = landordMail;
    }


    public void setDescription(String description) {
        this.description = description;
    }

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
