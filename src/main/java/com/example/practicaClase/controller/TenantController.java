package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.entities.Tenant;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    TenantRepository tenantRepository;


    @PostMapping("/new")
    public ResponseEntity<String> New(@RequestBody Tenant tenant) {
        tenantRepository.save(tenant);
        return ResponseEntity.ok("Owner created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tenant>>all(){
        return ResponseEntity.ok(tenantRepository.findAll());
    }



}
