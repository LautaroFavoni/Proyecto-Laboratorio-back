package com.example.practicaClase.controller;

import com.example.practicaClase.Config.JwtService;
import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Admin.AdminForCreation;
import com.example.practicaClase.persintence.entities.Admin;
import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.repository.AdminRepository;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional // Asegura que todas las operaciones se realicen en una transacción
    @PostMapping("/new")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminForCreation dto, @RequestHeader(value = "Authorization", required = false) String token)
 {
        try {
            //Extraer el rol del token
            //String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

            // Validar si el rol es "admin"
             //if (!"admin".equals(role)) {
             //    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
             //}
            //


            Admin admin = new Admin();
            admin.setMail(dto.getMail());
            admin.setName(dto.getName());
            admin.setRole("admin");
            // Encriptar la contraseña
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));

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

            // Devolver respuesta
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Admin: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> all(@RequestHeader("Authorization") String token) {
        // Extraer el rol del token
        String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

        // Validar si el rol es "admin"
        if (!"admin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
        }

        return ResponseEntity.ok(adminRepository.findAll());
    }

    // Método DELETE para eliminar un Admin por ID
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            // Extraer el rol del token
            String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

            // Validar si el rol es "admin"
            if (!"admin".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
            }

            // Buscar el Admin a eliminar
            Admin admin = adminRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

            // Desvincular todos los Owners asociados al Admin
            List<Owner> owners = admin.getOwnerList();
            if (owners != null) {
                for (Owner owner : owners) {
                    owner.setAdmin(null); // Eliminar la referencia al Admin
                    ownerRepository.save(owner); // Guardar los cambios en cada Owner
                }
                admin.getOwnerList().clear(); // Limpiar la lista de Owners en el Admin
            }

            // Eliminar el Admin después de desvincular las relaciones
            adminRepository.delete(admin);
            return ResponseEntity.ok("Admin deleted successfully");

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Admin");
        }
    }
}
