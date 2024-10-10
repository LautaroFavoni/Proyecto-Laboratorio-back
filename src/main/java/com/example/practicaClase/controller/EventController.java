package com.example.practicaClase.controller;

import com.example.practicaClase.persintence.entities.Event;
import com.example.practicaClase.persintence.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Event>> all(){
        // Trae todos los usuarios desde la base de datos
        List<Event> listEvents = eventRepository.findAll();
        return ResponseEntity.ok(listEvents);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        long count = eventRepository.count();
        return ResponseEntity.ok(count);
    }

}

