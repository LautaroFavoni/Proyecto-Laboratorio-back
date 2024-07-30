package com.example.practicaClase.controller;



import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
