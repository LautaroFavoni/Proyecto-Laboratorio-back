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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PostMapping("new1")
    public ResponseEntity<Payments> createPayment1(@RequestBody PaymentsForCreation dto) {
        Tenant tenant = tenantRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        Landlord landlord = landlordRepository.findById(dto.getLandlordId())
                .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));

        // Verificar si se proporcionó una fecha; si no, establecer la fecha actual
        LocalDateTime date = dto.getDate() != null ? dto.getDate() : LocalDateTime.now();



        // Crear y guardar el objeto Payments
        Payments payment = new Payments(date, tenant, property, landlord, dto.getAmount());
        paymentsRepository.save(payment);

        return ResponseEntity.ok(payment);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createPayment(@RequestBody PaymentsForCreation dto) {

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

            // Crear y guardar el objeto Payments
            Payments payment = new Payments(date, tenant, property, landlord, dto.getAmount());
            paymentsRepository.save(payment);

            return ResponseEntity.ok(payment);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Pago.");
        }
    }




    @GetMapping("/all")
    public ResponseEntity<List<Payments>>all(){
        return ResponseEntity.ok(paymentsRepository.findAll());
    }
}


