package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Payments.PaymentsForCreation;
import com.example.practicaClase.persintence.entities.Landlord;
import com.example.practicaClase.persintence.entities.Payments;
import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.entities.Tenant;
import com.example.practicaClase.persintence.repository.LandlordRepository;
import com.example.practicaClase.persintence.repository.PaymentsRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Payments")
public class PaymentsController {

    @Autowired
    PaymentsRepository paymentsRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private LandlordRepository landlordRepository;


    @PostMapping("/new-entidades-completas")
    public ResponseEntity<String> New(@RequestBody Payments payments) {

        paymentsRepository.save(payments);
        return ResponseEntity.ok("Owner created successfully");
    }

    @PostMapping("new")
    public ResponseEntity<Payments> createPayment(@RequestBody PaymentsForCreation dto) {
        Tenant tenant = tenantRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        Landlord landlord = landlordRepository.findById(dto.getLandlordId())
                .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));

        // Crear y guardar el objeto Payments
        Payments payment = new Payments(dto.getDate(), tenant, property, landlord, dto.getAmount());
        paymentsRepository.save(payment);

        return ResponseEntity.ok(payment);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payments>>all(){
        return ResponseEntity.ok(paymentsRepository.findAll());
    }
}


