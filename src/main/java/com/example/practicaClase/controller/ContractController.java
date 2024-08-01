package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.Contract;
import com.example.practicaClase.persintence.entities.Payments;
import com.example.practicaClase.persintence.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Contract")
public class ContractController {
    @Autowired
    ContractRepository contractRepository;


    @PostMapping ("new")
    public ResponseEntity<String> New(@RequestBody Contract contract) {

        contractRepository.save(contract);
        return ResponseEntity.ok("contract created successfully");
    }
}
