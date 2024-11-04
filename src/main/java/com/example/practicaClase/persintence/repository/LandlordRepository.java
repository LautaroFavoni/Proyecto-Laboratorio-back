package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandlordRepository extends JpaRepository<Landlord,Long> {
    Optional<Landlord> findByMail(String mail);
}
