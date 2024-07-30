package com.example.practicaClase.persintence.repository;

import com.example.practicaClase.persintence.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
