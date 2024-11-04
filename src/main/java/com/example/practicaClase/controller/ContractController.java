package com.example.practicaClase.controller;

import com.example.practicaClase.exceptions.ResourceNotFoundException;
import com.example.practicaClase.persintence.DTOs.Contract.ContractForCreation;
import com.example.practicaClase.persintence.DTOs.Contract.ContractResponseDTO;
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


    @PostMapping ("new-entidades-completas")
    public ResponseEntity<String> New(@RequestBody Contract contract) {

        contractRepository.save(contract);
        return ResponseEntity.ok("contract created successfully");
    }

    @Transactional

    // Método para convertir un Contract en un ContractResponseDTO
    public ContractResponseDTO convertToDTO(Contract contract) {
        return new ContractResponseDTO(
                contract.getId(),
                contract.getDate(),
                contract.getTenant().getMail(),
                contract.getProperty().getId(),
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
        }
        catch (Exception e){
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

    @PostMapping ("/by-tenant-mail")
    public ResponseEntity<?> getContractsByTenantMail(@RequestBody String tenantMail) {
        // Buscar contratos asociados al correo electrónico del tenant
        List<Contract> contracts = contractRepository.findByTenant_Mail(tenantMail);

        // Verificar si la lista está vacía y devolver un mensaje adecuado
        if (contracts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No contracts found for tenant with email: " + tenantMail);
        }

        // Convertir los contratos encontrados a ContractResponseDTO
        List<ContractResponseDTO> contractDTOs = contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(contractDTOs);
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


}
