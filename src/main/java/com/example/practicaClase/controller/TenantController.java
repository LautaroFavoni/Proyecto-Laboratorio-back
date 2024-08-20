package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Tenant.TenantForCreation;
import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.entities.Tenant;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PropertyRepository propertyRepository;



    @PostMapping("/new")
    public ResponseEntity<Tenant> createTenant(@RequestBody TenantForCreation dto) {
        // Buscar Owner por ID
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        // Buscar Property por ID
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        // Crear el nuevo Tenant
        Tenant tenant = new Tenant();
        tenant.setName(dto.getName());
        tenant.setPassword(dto.getPassword());
        tenant.setRole(dto.getRole());
        tenant.setOwner(owner);
        tenant.setProperty(property);

        // Guardar el Tenant en la base de datos
        tenantRepository.save(tenant);

        // Actualizar el Tenant en la Property y guardar
        property.setTenant(tenant);
        propertyRepository.save(property);

        // Agregar el Tenant a la lista de Tenants del Owner y actualizar el Owner
        owner.getTenantList().add(tenant);
        ownerRepository.save(owner);

        // Devolver respuesta
        return ResponseEntity.ok(tenant);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tenant>>all(){
        return ResponseEntity.ok(tenantRepository.findAll());
    }



}
