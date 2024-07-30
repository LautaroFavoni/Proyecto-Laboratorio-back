package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long> {
}
