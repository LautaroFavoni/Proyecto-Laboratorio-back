package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Long> {
}
