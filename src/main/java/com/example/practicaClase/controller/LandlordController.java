package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Landlord.LandlordForCreation;
import com.example.practicaClase.persintence.entities.Landlord;

import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.repository.LandlordRepository;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/new")
    public ResponseEntity<Landlord> createLandlord(@RequestBody LandlordForCreation dto) {
        // Buscar Owner por ID
        Owner owner = ownerRepository.findById(dto.getOwnerId())
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
        landlord.setName(dto.getMail());
        // Encriptar la contraseña
        landlord.setPassword(passwordEncoder.encode(dto.getPassword()));
        landlord.setRole(dto.getRole());
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



    @GetMapping("/all")
    public ResponseEntity<List<Landlord>> getAllLandlords() {
        List<Landlord> landlords = landlordRepository.findAll();
        return ResponseEntity.ok(landlords);
    }

}

