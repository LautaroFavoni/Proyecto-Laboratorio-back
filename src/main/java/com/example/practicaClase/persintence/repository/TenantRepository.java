package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant,Long> {
}
