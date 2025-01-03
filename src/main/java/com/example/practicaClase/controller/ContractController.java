package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Contract.ContractForCreation;
import com.example.practicaClase.persintence.DTOs.Contract.ContractResponseDTO;
import com.example.practicaClase.persintence.DTOs.Landlord.LandlordMailDTO;
import com.example.practicaClase.persintence.DTOs.Payments.TenantMailDTO;

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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contract")
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


    @Transactional

    // Método para convertir un Contract en un ContractResponseDTO
    public ContractResponseDTO convertToDTO(Contract contract) {
        return new ContractResponseDTO(
                contract.getId(),
                contract.getDate(),
                contract.getTenant().getMail(),
                contract.getProperty().getDescription(),
                contract.getLandlord().getMail(),
                contract.getEndDate()
        );
    }


    @PostMapping("new")
    public ResponseEntity<?> createContract(@RequestBody ContractForCreation dto) {

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

            // Crear el contrato
            Contract contract = new Contract(date, tenant, property, landlord, dto.getEndDate());
            contractRepository.save(contract);

            // Convertir a DTO para la respuesta
            ContractResponseDTO responseDTO = convertToDTO(contract);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Contrato");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContractResponseDTO>> all() {
        List<Contract> contracts = contractRepository.findAll();
        List<ContractResponseDTO> contractDTOs = contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contractDTOs);
    }

    // ContractController.java

    @PostMapping("/by-tenant-mail")
    public ResponseEntity<?> getContractsByTenantMail(@RequestBody TenantMailDTO dto) {


        Tenant tenant = tenantRepository.findByMail(dto.getTenantMail())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        try {
            List<Contract> contracts = contractRepository.findByTenantId(tenant.getId());


            // Verificar si la lista está vacía y devolver un mensaje adecuado
            if (contracts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No contracts found for tenant with email: " + dto.getTenantMail());
            }

            // Convertir los contratos encontrados a ContractResponseDTO
            List<ContractResponseDTO> contractDTOs = contracts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(contractDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el Contrato");
        }
    }

    @PostMapping("/by-landlord-mail")
    public ResponseEntity<?> getContractsByLandlordMail(@RequestBody LandlordMailDTO dto) {
        try {
            // Buscar el Landlord por su correo electrónico
            Landlord landlord = landlordRepository.findByMail(dto.getLandlordMail())
                    .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));

            // Obtener los contratos asociados al Landlord
            List<Contract> contracts = contractRepository.findByLandlordId(landlord.getId());

            // Verificar si la lista está vacía y devolver un mensaje adecuado
            if (contracts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No contracts found for landlord with email: " + dto.getLandlordMail());
            }

            // Convertir los contratos encontrados a ContractResponseDTO
            List<ContractResponseDTO> contractDTOs = contracts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(contractDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los contratos.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable Long id) {
        try {
            // Buscar el contrato por ID
            Contract contract = contractRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

            // Eliminar el contrato
            contractRepository.delete(contract);

            return ResponseEntity.ok("Contract deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el contrato");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContractDates(@PathVariable Long id, @RequestBody ContractForCreation dto) {
        try {
            // Buscar el contrato por ID
            Contract contract = contractRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

            // Actualizar la fecha de inicio si se proporciona una nueva en el DTO
            if (dto.getDate() != null) {
                contract.setDate(dto.getDate());
            }

            // Actualizar la fecha de fin si se proporciona en el DTO
            if (dto.getEndDate() != null) {
                contract.setEndDate(dto.getEndDate());
            }

            // Guardar el contrato actualizado
            contractRepository.save(contract);

            // Convertir a DTO para la respuesta
            ContractResponseDTO responseDTO = convertToDTO(contract);
            return ResponseEntity.ok(responseDTO);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


    }
}