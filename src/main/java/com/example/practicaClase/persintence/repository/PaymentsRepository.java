package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository <Payments, Long> {
}
