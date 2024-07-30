package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
