package com.example.practicaClase.controller;



import com.example.practicaClase.Config.JwtService;
import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Payments.TenantMailDTO;
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la propiedad");
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

    private boolean hasAdminOrOwnerRole(String token) {
        String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);
        return "admin".equals(role) || "owner".equals(role);
    }


    // Método DELETE para eliminar una propiedad por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            if (!hasAdminOrOwnerRole(token)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }

            Property property = propertyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

            // Desvincular la propiedad de Tenant
            Tenant tenant = property.getTenant();
            if (tenant != null) {
                tenant.setProperty(null);
                tenantRepository.save(tenant);
            }

            // Desvincular la propiedad de la lista en Landlord
            Landlord landlord = property.getLandlord();
            if (landlord != null && landlord.getPropertyList() != null) {
                landlord.getPropertyList().remove(property);  // Remover la propiedad de la lista
                landlordRepository.save(landlord);          // Guardar cambios
            }

            // Desvincular la propiedad de la lista en Owner
            Owner owner = property.getOwner();
            if (owner != null && owner.getPropertyList() != null) {
                owner.getPropertyList().remove(property);     // Remover la propiedad de la lista
                ownerRepository.save(owner);                // Guardar cambios
            }

            // Eliminar la propiedad después de desvincular las relaciones
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
    @PostMapping("/by-tenant-mail")
    public ResponseEntity<?> getPropertiesByTenantMail(@RequestBody TenantMailDTO dto) {
        try {
            // Buscar el Tenant por su correo electrónico
            Tenant tenant = tenantRepository.findByMail(dto.getTenantMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

            // Obtener la propiedad asociada al Tenant
            Property property = tenant.getProperty();

            // Verificar si el tenant tiene propiedades asociadas y devolver un mensaje adecuado
            if (property == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No property found for tenant with email: " + dto.getTenantMail());
            }

            // Convertir la propiedad encontrada a PropertyResponseDTO
            PropertyResponseDTO propertyDTO = getPropertyResponseDTO(property);

            return ResponseEntity.ok(propertyDTO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la propiedad.");
        }
    }



}
