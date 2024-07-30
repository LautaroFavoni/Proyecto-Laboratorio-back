package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
}
