package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Owner.OwnerForCreation;
import com.example.practicaClase.persintence.entities.Admin;
import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.repository.AdminRepository;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Owner")
public class OwnerController {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("new")
    public ResponseEntity<Owner> createOwner(@RequestBody OwnerForCreation dto) {
        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        // Crear y guardar el objeto Owner
        Owner owner = new Owner();
        owner.setName(dto.getMail());
        // Encriptar la contraseña
        owner.setPassword(passwordEncoder.encode(dto.getPassword()));
        owner.setRole(dto.getRole());
        owner.setAdmin(admin);

        // Agregar el Owner a la lista de Owners del Admin
        admin.getOwnerList().add(owner);

        // Guardar el Owner y actualizar el Admin
        ownerRepository.save(owner);
        adminRepository.save(admin); // Asegúrate de guardar el Admin para persistir la relación

        return ResponseEntity.ok(owner);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Owner>>all(){
        return ResponseEntity.ok(ownerRepository.findAll());
    }
}







