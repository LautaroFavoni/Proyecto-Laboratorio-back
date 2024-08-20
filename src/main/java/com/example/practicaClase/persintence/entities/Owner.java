package com.example.practicaClase.persintence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Owner extends User {

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Property> propertyList;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Landlord> landlordList;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Tenant> tenantList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Getters and Setters
    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public List<Landlord> getLandlordList() {
        return landlordList;
    }

    public void setLandlordList(List<Landlord> landlordList) {
        this.landlordList = landlordList;
    }

    public List<Tenant> getTenantList() {
        return tenantList;
    }

    public void setTenantList(List<Tenant> tenantList) {
        this.tenantList = tenantList;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
