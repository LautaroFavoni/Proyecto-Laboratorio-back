package com.example.practicaClase.controller;



import com.example.practicaClase.Config.JwtService;
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

    @Autowired
    private JwtService jwtService;

    @Transactional




    @PostMapping("new")

    public ResponseEntity<?> createProperty(@RequestBody PropertyForCreation dto) {
        try {



            Tenant tenant = tenantRepository.findByMail(dto.getTenantMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
            Landlord landlord = landlordRepository.findByMail(dto.getLandlordMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));
            Owner owner = ownerRepository.findByMail(dto.getOwnerMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
            // Crear y guardar el objeto Property
            Property property = new Property();
            property.setTenant(tenant);
            property.setLandlord(landlord);
            property.setOwner(owner);
            property.setAddress(dto.getAddress());
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
        dtoResponse.setAddress(property.getAddress());
        dtoResponse.setDescription(property.getDescription());

        // Asignar correos electrónicos con comprobación de null
        dtoResponse.setTenantMail(property.getTenant() != null ? property.getTenant().getMail() : null);
        dtoResponse.setLandlordMail(property.getLandlord() != null ? property.getLandlord().getMail() : null);
        dtoResponse.setOwnerMail(property.getOwner() != null ? property.getOwner().getMail() : null);

        return dtoResponse;
    }


    @GetMapping("/all")
    public ResponseEntity<List<PropertyResponseDTO>> all() {
        List<PropertyResponseDTO> dtoList = propertyRepository.findAll().stream().map(property -> {
            PropertyResponseDTO dto = new PropertyResponseDTO();
            dto.setId(property.getId());
            dto.setAddress(property.getAddress());
            dto.setDescription(property.getDescription());

            // Asignar correos electrónicos en lugar de IDs
            dto.setTenantMail(property.getTenant() != null ? property.getTenant().getMail() : null);
            dto.setLandlordMail(property.getLandlord() != null ? property.getLandlord().getMail() : null);
            dto.setOwnerMail(property.getOwner() != null ? property.getOwner().getMail() : null);

            return dto;
        }).toList();

        return ResponseEntity.ok(dtoList);
    }


    // Método DELETE para eliminar una propiedad por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            // Validar el rol en el token
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);
            if (!"admin".equals(role) && !"owner".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }

            Property property = propertyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
            propertyRepository.delete(property);
            return ResponseEntity.ok("Property deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la propiedad");
        }
    }

    // Método PUT para actualizar una propiedad existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody PropertyForCreation dto, @RequestHeader("Authorization") String token) {
        try {
            // Validar el rol en el token
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);
            if (!"admin".equals(role) && !"owner".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }

            Property property = propertyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

            Tenant tenant = tenantRepository.findByMail(dto.getTenantMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
            Landlord landlord = landlordRepository.findByMail(dto.getLandlordMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));
            Owner owner = ownerRepository.findByMail(dto.getOwnerMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

            // Actualizar los campos de la propiedad
            property.setTenant(tenant);
            property.setLandlord(landlord);
            property.setOwner(owner);
            property.setAddress(dto.getAddress());
            property.setDescription(dto.getDescription());

            propertyRepository.save(property);
            PropertyResponseDTO dtoResponse = getPropertyResponseDTO(property);

            return ResponseEntity.ok(dtoResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la propiedad");
        }
    }



}
