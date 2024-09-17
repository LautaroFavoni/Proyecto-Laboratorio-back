package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Admin.AdminForCreation;
import com.example.practicaClase.persintence.entities.Admin;

import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.repository.AdminRepository;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new")
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminForCreation dto) {
        // Crear un nuevo Admin
        Admin admin = new Admin();
        admin.setName(dto.getMail());
        // Encriptar la contraseña
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(dto.getRole());

        // Guardar el Admin en la base de datos
        adminRepository.save(admin);

        // Opcionalmente, actualizar la relación con los Owners
        if (dto.getOwnerIds() != null) {
            for (Long ownerId : dto.getOwnerIds()) {
                Owner owner = ownerRepository.findById(ownerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
                owner.setAdmin(admin);
                ownerRepository.save(owner);

                // Agregar el Owner a la lista de Owners del Admin
                admin.getOwnerList().add(owner);
            }
        }

        // Guardar el Admin actualizado
        adminRepository.save(admin);

        // Devolver respuesta
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>>all(){
        return ResponseEntity.ok(adminRepository.findAll());
    }
}

