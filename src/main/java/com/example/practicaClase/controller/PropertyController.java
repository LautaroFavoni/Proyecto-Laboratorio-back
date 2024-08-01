package com.example.practicaClase.controller;



import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    PropertyRepository propertyRepository;


    @PostMapping("/new")
    public ResponseEntity<String> New(@RequestBody Property property) {
        propertyRepository.save(property);
        return ResponseEntity.ok("Owner created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Property>>all(){
        return ResponseEntity.ok(propertyRepository.findAll());
    }





}
