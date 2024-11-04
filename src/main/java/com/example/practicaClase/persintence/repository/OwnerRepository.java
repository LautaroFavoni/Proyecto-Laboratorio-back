package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
    Optional<Owner> findByMail(String mail);
}
