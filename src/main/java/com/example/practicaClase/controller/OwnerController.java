package com.example.practicaClase.controller;

import com.example.practicaClase.Config.JwtService;
import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Owner.OwnerForCreation;
import com.example.practicaClase.persintence.entities.Admin;
import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.repository.AdminRepository;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.service.OwnerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Transactional


    @PostMapping("new")
    public ResponseEntity<?> createOwner(@Valid @RequestBody OwnerForCreation dto,@RequestHeader("Authorization") String token) {

        try {
            // Extraer el rol del token JWT
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

            // Verificar si el rol es "admin" o "owner"
            if (!"admin".equals(role) && !"owner".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }


            Admin admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

            // Crear y guardar el objeto Owner
            Owner owner = new Owner();
            owner.setMail(dto.getMail());
            owner.setName(dto.getName());
            // Encriptar la contraseña
            owner.setPassword(passwordEncoder.encode(dto.getPassword()));
            owner.setRole("owner");
            owner.setAdmin(admin);

            // Agregar el Owner a la lista de Owners del Admin
            admin.getOwnerList().add(owner);

            // Guardar el Owner y actualizar el Admin
            ownerRepository.save(owner);
            adminRepository.save(admin); // Asegúrate de guardar el Admin para persistir la relación

            return ResponseEntity.ok(owner);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Owner.");
        }
    }

    @Transactional



    @GetMapping("/all")
    public ResponseEntity<?>all(@RequestHeader("Authorization") String token){
        // Extraer el rol del token JWT
        String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

        // Verificar si el rol es "admin" o "owner"
        if (!"admin".equals(role) && !"owner".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
        }


        return ResponseEntity.ok(ownerRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerForCreation dto, @RequestHeader("Authorization") String token) {
        try {
            // Extraer el rol del token JWT
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

            // Verificar si el rol es "admin" o "owner"
            if (!"admin".equals(role) && !"owner".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }

            // Buscar el Owner por ID
            Owner existingOwner = ownerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

            // Buscar el Admin por ID
            Admin admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

            // Actualizar los datos del Owner
            existingOwner.setMail(dto.getMail());
            existingOwner.setPassword(passwordEncoder.encode(dto.getPassword()));
            existingOwner.setAdmin(admin);

            // Guardar los cambios en la base de datos
            ownerRepository.save(existingOwner);

            return ResponseEntity.ok(existingOwner);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el Owner.");
        }
    }

    // Método DELETE para eliminar un Owner por ID
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            // Extraer el rol del token JWT
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

            // Verificar si el rol es "admin" o "owner"
            if (!"admin".equals(role) && !"owner".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }

            // Buscar el Owner a eliminar
            Owner owner = ownerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

            // Desvincular el Owner de su Admin asociado, si existe
            Admin admin = owner.getAdmin();
            if (admin != null) {
                admin.getOwnerList().remove(owner);
                adminRepository.save(admin); // Guardar los cambios en el Admin
            }

            // Eliminar el Owner de la base de datos
            ownerRepository.delete(owner);

            return ResponseEntity.ok("Owner deleted successfully");

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Owner.");
        }
    }

}







