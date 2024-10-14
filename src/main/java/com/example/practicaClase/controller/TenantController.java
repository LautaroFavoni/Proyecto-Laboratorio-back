package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Tenant.TenantForCreation;
import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.entities.Tenant;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional


    @PostMapping("/new")
    public ResponseEntity<?> createTenant(@Valid @RequestBody TenantForCreation dto) {
        try {

            // Buscar Owner por ID
            Owner owner = ownerRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

            // Buscar Property por ID, permitiendo que la propiedad sea null
            Property property = dto.getPropertyId() != null ?
                    propertyRepository.findById(dto.getPropertyId())
                            .orElseThrow(() -> new ResourceNotFoundException("Property not found")) :
                    null;

            // Crear el nuevo Tenant
            Tenant tenant = new Tenant();
            tenant.setMail(dto.getMail());
            // Encriptar la contraseña
            tenant.setPassword(passwordEncoder.encode(dto.getPassword()));
            tenant.setRole(dto.getRole());
            tenant.setOwner(owner);
            tenant.setProperty(property);

            // Guardar el Tenant en la base de datos
            tenantRepository.save(tenant);

            // Actualizar el Tenant en la Property y guardar si la Property no es null
            if (property != null) {
                property.setTenant(tenant);
                propertyRepository.save(property);
            }

            // Agregar el Tenant a la lista de Tenants del Owner, asegurando que la lista no sea null
            if (owner.getTenantList() == null) {
                owner.setTenantList(new ArrayList<>());
            }
            owner.getTenantList().add(tenant);
            ownerRepository.save(owner);

            // Devolver respuesta
            return ResponseEntity.ok(tenant);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Tenant.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tenant>>all(){
        return ResponseEntity.ok(tenantRepository.findAll());
    }

}
