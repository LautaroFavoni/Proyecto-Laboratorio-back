package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
