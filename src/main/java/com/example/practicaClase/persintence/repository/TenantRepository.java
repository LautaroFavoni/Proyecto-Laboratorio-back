package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant,Long> {
    Optional<Tenant> findByMail(String mail);



}
