package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.Payments;
import com.example.practicaClase.persintence.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Payments")
public class PaymentsController {

    @Autowired
    PaymentsRepository paymentsRepository;


    @PostMapping("/new")
    public ResponseEntity<String> New(@RequestBody Payments payments) {

        paymentsRepository.save(payments);
        return ResponseEntity.ok("Owner created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payments>>all(){
        return ResponseEntity.ok(paymentsRepository.findAll());
    }
}


