package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Landlord.LandlordMailDTO;
import com.example.practicaClase.persintence.DTOs.Payments.PaymentsForCreation;
import com.example.practicaClase.persintence.DTOs.Payments.PaymentsResponseDTO;
import com.example.practicaClase.persintence.DTOs.Payments.TenantMailDTO;
import com.example.practicaClase.persintence.entities.Landlord;
import com.example.practicaClase.persintence.entities.Payments;
import com.example.practicaClase.persintence.entities.Property;
import com.example.practicaClase.persintence.entities.Tenant;
import com.example.practicaClase.persintence.repository.LandlordRepository;
import com.example.practicaClase.persintence.repository.PaymentsRepository;
import com.example.practicaClase.persintence.repository.PropertyRepository;
import com.example.practicaClase.persintence.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    PaymentsRepository paymentsRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private LandlordRepository landlordRepository;


    @Transactional
    public PaymentsResponseDTO convertToDTO(Payments payment) {
        return new PaymentsResponseDTO(
                payment.getId(),
                payment.getDate(),
                payment.getTenant().getMail(),
                payment.getProperty().getDescription(),
                payment.getLandlord().getMail(),
                payment.getAmount()
        );
    }



    @PostMapping("/new")
    public ResponseEntity<?> createPayment(@RequestBody PaymentsForCreation dto) {

        try {

            // Buscar el Tenant por ID
            Tenant tenant = tenantRepository.findByMail(dto.getTenantMail())
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

            PaymentsResponseDTO responseDTO = convertToDTO(payment);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Pago.");
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<PaymentsResponseDTO>> all() {
        List<Payments> payments = paymentsRepository.findAll();
        List<PaymentsResponseDTO> responseDTOs = payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        try {
            // Buscar el Payment por ID
            Payments payment = paymentsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

            // Eliminar el Payment de la base de datos
            paymentsRepository.delete(payment);

            // Devolver respuesta de éxito
            return ResponseEntity.ok("Payment deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el Pago.");
        }
    }

    @PostMapping("/by-tenant-mail")
    public ResponseEntity<?> getPaymentsByTenantMail(@RequestBody TenantMailDTO dto) {
        try {
            // Buscar el Tenant por su correo electrónico
            Tenant tenant = tenantRepository.findByMail(dto.getTenantMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

            // Obtener los pagos asociados al Tenant
            List<Payments> payments = paymentsRepository.findByTenantId(tenant.getId());

            // Verificar si la lista está vacía y devolver un mensaje adecuado
            if (payments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No payments found for tenant with email: " + dto.getTenantMail());
            }

            // Convertir los pagos encontrados a PaymentsResponseDTO
            List<PaymentsResponseDTO> paymentDTOs = payments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(paymentDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los pagos.");
        }
    }

    @PostMapping("/payments-by-landlord-mail")
    public ResponseEntity<?> getPaymentsByLandlordMail(@RequestBody LandlordMailDTO landlordMail) {
        try {
            // Buscar el Landlord por su correo electrónico
            Landlord landlord = landlordRepository.findByMail(landlordMail.getLandlordMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));

            // Obtener los correos electrónicos de los Tenants asociados a ese Landlord
            List<String> tenantMails = tenantRepository.findTenantMailsByLandlordId(landlord.getId());

            // Si no se encontraron Tenants, devolver un mensaje adecuado
            if (tenantMails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No tenants found for landlord with email: " + landlordMail.getLandlordMail());
            }

            // Buscar los Tenants por sus correos electrónicos
            List<Tenant> tenants = tenantRepository.findByMailIn(tenantMails);

            // Si no se encontraron Tenants, devolver un mensaje adecuado
            if (tenants.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No tenants found with the provided emails");
            }

            // Obtener los pagos asociados a los Tenants encontrados
            List<Long> tenantIds = tenants.stream().map(Tenant::getId).collect(Collectors.toList());
            List<Payments> payments = paymentsRepository.findByTenantIdIn(tenantIds);

            // Si no hay pagos, devolver un mensaje adecuado
            if (payments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No payments found for the provided tenants");
            }

            // Convertir los pagos encontrados a PaymentsResponseDTO
            List<PaymentsResponseDTO> paymentDTOs = payments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(paymentDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los pagos.");
        }
    }





}
