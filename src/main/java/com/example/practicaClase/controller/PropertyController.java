package com.example.practicaClase.controller;



import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Property.PropertyForCreation;
import com.example.practicaClase.persintence.DTOs.Property.PropertyResponseDTO;
import com.example.practicaClase.persintence.entities.Landlord;
import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.entities.Tenant;
import com.example.practicaClase.persintence.repository.LandlordRepository;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional



    @PostMapping("/new-entidades-completas")
    public ResponseEntity<String> New(@RequestBody Property property) {
        propertyRepository.save(property);
        return ResponseEntity.ok("Owner created successfully");
    }

    @Transactional


    @PostMapping("new")

    public ResponseEntity<?> createProperty(@RequestBody PropertyForCreation dto) {
        try {


            Tenant tenant = tenantRepository.findById(dto.getTenantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
            Landlord landlord = landlordRepository.findById(dto.getLandlordId())
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));
            Owner owner = ownerRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

            // Crear y guardar el objeto Property
            Property property = new Property();
            property.setTenant(tenant);
            property.setLandlord(landlord);
            property.setOwner(owner);
            property.setAdress(dto.getAddress());
            property.setDescription(dto.getDescription());

            tenant.setProperty(property);







            propertyRepository.save(property);

            PropertyResponseDTO dtoResponse = getPropertyResponseDTO(property);

            return ResponseEntity.ok(dtoResponse);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el PAGO.");
        }
    }

    private static PropertyResponseDTO getPropertyResponseDTO(Property property) {
        PropertyResponseDTO dtoResponse = new PropertyResponseDTO();
        dtoResponse.setId(property.getId());
        dtoResponse.setAdress(property.getAdress());
        dtoResponse.setDescription(property.getDescription());
        dtoResponse.setTenantId(property.getTenant() != null ? property.getTenant().getId() : null);
        dtoResponse.setLandlordId(property.getLandlord() != null ? property.getLandlord().getId() : null);
        dtoResponse.setOwnerId(property.getOwner() != null ? property.getOwner().getId() : null);
        return dtoResponse;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PropertyResponseDTO>> all() {
        List<PropertyResponseDTO> dtoList = propertyRepository.findAll().stream().map(property -> {
            PropertyResponseDTO dto = new PropertyResponseDTO();
            dto.setId(property.getId());
            dto.setAdress(property.getAdress());
            dto.setDescription(property.getDescription());
            dto.setTenantId(property.getTenant() != null ? property.getTenant().getId() : null);
            dto.setLandlordId(property.getLandlord() != null ? property.getLandlord().getId() : null);
            dto.setOwnerId(property.getOwner() != null ? property.getOwner().getId() : null);
            return dto;
        }).toList();

        return ResponseEntity.ok(dtoList);
    }


}
