package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.Admin;

import com.example.practicaClase.persintence.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @PostMapping("/new")
    public ResponseEntity<String> New(@RequestBody Admin admin) {
        adminRepository.save(admin);
        return ResponseEntity.ok("Owner created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>>all(){
        return ResponseEntity.ok(adminRepository.findAll());
    }
}

