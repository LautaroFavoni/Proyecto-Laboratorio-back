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
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/Contract")
@CrossOrigin(origins = "**")
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

        // Verificar si se proporcionó una fecha; si no, establecer la fecha actual
        String date = dto.getDate() != null ? dto.getDate() : getCurrentDateTimeAsString();

        Contract contract = new Contract(date, tenant, property, landlord, dto.getEndDate());
        contractRepository.save(contract);

        return ResponseEntity.ok(contract);
    }

    // Método para obtener la fecha y hora actual como cadena
    private String getCurrentDateTimeAsString() {
        // Formato de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // Convertir a cadena con el formato especificado
        return now.format(formatter);
    }

}
