package com.example.practicaClase.controller;

import com.example.practicaClase.Config.JwtService;
import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.entities.User;
import com.example.practicaClase.persintence.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<User>> all(){
        // Trae todos los usuarios desde la base de datos
        List<User> listUsers = userRepository.findAll();
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        long count = userRepository.count();
        return ResponseEntity.ok(count);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }

    @Autowired
    private JwtService jwtService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id, @RequestHeader(value = "Authorization") String token)
    {

        // Extraer el rol del token JWT
        String role = jwtService.extractClaims(token.replace("Bearer ", "")).get("role", String.class);

        // Verificar si el rol es "admin" o "owner"
        if (!"admin".equals(role) && !"owner".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para realizar esta acción.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
        return ResponseEntity.ok().body("User deleted successfully");
    }


}
