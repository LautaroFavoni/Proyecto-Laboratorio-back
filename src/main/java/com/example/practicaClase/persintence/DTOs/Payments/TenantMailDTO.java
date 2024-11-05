package com.example.practicaClase.persintence.DTOs.Payments;

public class TenantMailDTO {

    public TenantMailDTO(String tenantMail) {
        this.tenantMail = tenantMail;
    }

    public TenantMailDTO() {

    }

    public String getTenantMail() {
        return tenantMail;
    }

    public void setTenantMail(String tenantMail) {
        this.tenantMail = tenantMail;
    }

    private String tenantMail;



}
