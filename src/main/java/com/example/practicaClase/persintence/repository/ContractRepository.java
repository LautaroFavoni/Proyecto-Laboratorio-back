package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Contract;
import com.example.practicaClase.persintence.entities.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findByTenantMail(String tenantMail);

    List<Contract> findByTenantId(Long tenantId);

}
