package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Contract.ContractForCreation;
import com.example.practicaClase.persintence.entities.*;
import com.example.practicaClase.persintence.repository.ContractRepository;
import com.example.practicaClase.persintence.repository.LandlordRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/Contract")
@CrossOrigin(origins = "http://localhost:5173")
public class ContractController {
    @Autowired
    ContractRepository contractRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private LandlordRepository landlordRepository;


    @PostMapping ("new-entidades-completas")
    public ResponseEntity<String> New(@RequestBody Contract contract) {

        contractRepository.save(contract);
        return ResponseEntity.ok("contract created successfully");
    }

    @PostMapping("new")
    public ResponseEntity<Contract> createContract(@RequestBody ContractForCreation dto) {
        Tenant tenant = tenantRepository.findById(dto.getTenantId()).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        Landlord landlord = landlordRepository.findById(dto.getLandlordId()).orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));

        // Establecer la fecha actual
        String currentDate = LocalDateTime.now().toString();

        Contract contract = new Contract(currentDate, tenant, property, landlord, dto.getEndDate());
        contractRepository.save(contract);

        return ResponseEntity.ok(contract);
    }

}
