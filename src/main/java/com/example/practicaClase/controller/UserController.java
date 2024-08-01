package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.User;
import com.example.practicaClase.persintence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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
}
