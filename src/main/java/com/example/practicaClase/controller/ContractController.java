package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Contract.ContractForCreation;
import com.example.practicaClase.persintence.entities.*;
import com.example.practicaClase.persintence.repository.ContractRepository;
import com.example.practicaClase.persintence.repository.LandlordRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @Transactional


    @PostMapping("new")
    public ResponseEntity<?> createContract(@RequestBody ContractForCreation dto) {

        try {


            // Buscar el Tenant por ID
            Tenant tenant = tenantRepository.findById(dto.getTenantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

            // Obtener la Property asociada al Tenant
            Property property = tenant.getProperty(); // Asegúrate de que `getProperty()` no sea null

            if (property == null) {
                throw new ResourceNotFoundException("Property not found for the given Tenant");
            }

            // Obtener el Landlord desde la Property
            Landlord landlord = property.getLandlord(); // Asegúrate de que `getLandlord()` no sea null

            if (landlord == null) {
                throw new ResourceNotFoundException("Landlord not found for the given Property");
            }

            // Verificar si se proporcionó una fecha; si no, establecer la fecha actual
            LocalDateTime date = dto.getDate() != null ? dto.getDate() : LocalDateTime.now();

            Contract contract = new Contract(date, tenant, property, landlord, dto.getEndDate());
            contractRepository.save(contract);

            return ResponseEntity.ok(contract);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Contrato");
        }
    }


}
