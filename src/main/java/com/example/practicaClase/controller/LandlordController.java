package com.example.practicaClase.controller;

import com.example.practicaClase.Config.JwtService;
import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Landlord.LandlordForCreation;
import com.example.practicaClase.persintence.entities.Landlord;

import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.repository.LandlordRepository;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/landlord")
public class LandlordController {

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional


    @PostMapping("/new")
    public ResponseEntity<?> createLandlord(@Valid @RequestBody LandlordForCreation dto) {
        try {
            // Buscar Owner por ID

            long id =4;
            Owner owner = ownerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

            // Verificar si propertyIds es null y manejar el caso
            List<Property> properties = dto.getPropertyIds() == null ?
                    Collections.emptyList() :
                    dto.getPropertyIds().stream()
                            .map(propertyId -> propertyRepository.findById(propertyId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Property not found")))
                            .collect(Collectors.toList());

            // Crear el nuevo Landlord
            Landlord landlord = new Landlord();
            landlord.setMail(dto.getMail());
            // Encriptar la contraseña
            landlord.setPassword(passwordEncoder.encode(dto.getPassword()));
            landlord.setRole("landlord");
            landlord.setName(dto.getName());
            landlord.setOwner(owner);
            landlord.setPropertyList(properties);

            // Guardar el Landlord en la base de datos
            landlordRepository.save(landlord);

            // Agregar el Landlord a la lista de Landlords del Owner y actualizar el Owner
            if (owner.getLandlordList() == null) {
                owner.setLandlordList(Collections.emptyList());
            }
            owner.getLandlordList().add(landlord);
            ownerRepository.save(owner);

            // Devolver respuesta
            return ResponseEntity.ok(landlord);
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Admin.");
        }
    }

    // Método DELETE para eliminar un Landlord por ID
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLandlord(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
        try {
            // Extraer el rol del token
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

            // Validar si el rol es "admin"
            if (!"admin".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }

            // Buscar el Landlord a eliminar
            Landlord landlord = landlordRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));

            // Desvincular las propiedades asociadas al Landlord
            List<Property> properties = landlord.getPropertyList();
            if (properties != null) {
                for (Property property : properties) {
                    property.setLandlord(null); // Desvincular la propiedad del Landlord
                    propertyRepository.save(property); // Guardar el cambio en la propiedad
                }
                landlord.getPropertyList().clear(); // Limpiar la lista de propiedades en el Landlord
            }

            // Desvincular el Landlord del Owner asociado
            Owner owner = landlord.getOwner();
            if (owner != null) {
                owner.getLandlordList().remove(landlord); // Eliminar el Landlord de la lista del Owner
                ownerRepository.save(owner); // Guardar el cambio en el Owner
            }

            // Eliminar el Landlord después de desvincular las relaciones
            landlordRepository.delete(landlord);
            return ResponseEntity.ok("Landlord deleted successfully");

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Landlord");
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateLandlord(@PathVariable Long id, @Valid @RequestBody LandlordForCreation dto,@RequestHeader(value = "Authorization") String token) {
        try {

            //Extraer el rol del token
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

            // Validar si el rol es "admin"
            if (!"admin".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }
            // Buscar el Landlord por ID
            Landlord landlord = landlordRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));

            // Buscar el Owner por el ID proporcionado en el DTO
            Owner owner = ownerRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

            // Verificar y actualizar la lista de propiedades
            List<Property> properties = dto.getPropertyIds() == null ?
                    Collections.emptyList() :
                    dto.getPropertyIds().stream()
                            .map(propertyId -> propertyRepository.findById(propertyId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Property not found")))
                            .collect(Collectors.toList());

            // Actualizar los campos del Landlord
            landlord.setMail(dto.getMail());
            landlord.setName(dto.getName());
            if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                landlord.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            landlord.setOwner(owner);
            landlord.setPropertyList(properties);

            // Guardar los cambios en el Landlord
            landlordRepository.save(landlord);

            // Actualizar la lista de Landlords del Owner si es necesario
            if (owner.getLandlordList() == null) {
                owner.setLandlordList(Collections.emptyList());
            }
            if (!owner.getLandlordList().contains(landlord)) {
                owner.getLandlordList().add(landlord);
            }
            ownerRepository.save(owner);

            // Devolver respuesta exitosa
            return ResponseEntity.ok(landlord);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el Landlord.");
        }
    }

    @Transactional



    @GetMapping("/all")
    public ResponseEntity<List<Landlord>> getAllLandlords() {
        List<Landlord> landlords = landlordRepository.findAll();
        return ResponseEntity.ok(landlords);
    }

}

