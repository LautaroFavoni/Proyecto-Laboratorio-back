package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Contract;
import com.example.practicaClase.persintence.entities.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsRepository extends JpaRepository <Payments, Long> {

    List<Payments> findByTenantId(Long tenantId);

    List<Payments> findByLandlordId(Long landlordId);
}
