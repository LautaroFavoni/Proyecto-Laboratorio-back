package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import com.example.practicaClase.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Owner")
public class OwnerController {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @PostMapping("/new")
    public ResponseEntity<String> New(@RequestBody Owner owner) {
        ownerService.newOwner(owner);
        return ResponseEntity.ok("Owner created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Owner>>all(){
        return ResponseEntity.ok(ownerRepository.findAll());
    }
}







