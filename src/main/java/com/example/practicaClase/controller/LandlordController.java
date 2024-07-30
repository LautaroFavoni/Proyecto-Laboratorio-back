package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.Landlord;

import com.example.practicaClase.persintence.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landlord")
public class LandlordController {

    @Autowired
    private LandlordRepository landlordRepository;

    @PostMapping("/new")
    public ResponseEntity<String> newLandlord(@RequestBody Landlord landlord) {
        landlordRepository.save(landlord);
        return ResponseEntity.ok("Landlord created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Landlord>> getAllLandlords() {
        List<Landlord> landlords = landlordRepository.findAll();
        return ResponseEntity.ok(landlords);
    }

}

