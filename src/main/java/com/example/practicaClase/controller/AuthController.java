package com.example.practicaClase.controller;

import com.example.practicaClase.Config.JwtResponse;
import com.example.practicaClase.Config.JwtService;
import com.example.practicaClase.persintence.DTOs.User.UserForLogin;
import com.example.practicaClase.persintence.entities.User;
import com.example.practicaClase.persintence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping()
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

   /*
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }

        // Verificar si la contraseña no es nula o vacía
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La contraseña no puede ser nula o vacía");
        }

        // Encriptar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Guardar el nuevo usuario
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
    }

    */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserForLogin loginUser) {
        Optional<User> optionalUser = userRepository.findByname(loginUser.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getName());
                return ResponseEntity.ok(new JwtResponse(token));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}

