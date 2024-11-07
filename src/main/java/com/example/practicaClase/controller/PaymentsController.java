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
import org.springframework.dao.DataAccessException;
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

    @PostMapping("/by-landlord-mail")
    public ResponseEntity<?> getPaymentsByLandlord(@RequestBody LandlordMailDTO dto) {
        try {
            Landlord landlord = landlordRepository.findByMail(dto.getLandlordMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found with email: " + dto.getLandlordMail()));

            // Obtener los pagos asociados al Landlord
            List<Payments> payments = paymentsRepository.findByLandlordId(landlord.getId());

            if (payments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No payments found for landlord with email: " + dto.getLandlordMail());
            }

            List<PaymentsResponseDTO> paymentDTOs = payments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(paymentDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los pagos.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable Long id, @RequestBody PaymentsForCreation dto) {
        try {
            // Buscar el Payment por ID
            Payments payment = paymentsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

            // Actualizar los datos del pago
            payment.setAmount(dto.getAmount());
            if (dto.getDate() != null) {
                payment.setDate(dto.getDate());
            }

            // Guardar los cambios en la base de datos
            paymentsRepository.save(payment);

            // Convertir a DTO y devolver la respuesta
            PaymentsResponseDTO responseDTO = convertToDTO(payment);
            return ResponseEntity.ok(responseDTO);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el pago.");
        }
    }



}
