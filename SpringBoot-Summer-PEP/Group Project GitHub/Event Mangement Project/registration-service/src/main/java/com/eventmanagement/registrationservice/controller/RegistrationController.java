package com.eventmanagement.registrationservice.controller;

import com.eventmanagement.registrationservice.entity.Registration;
import com.eventmanagement.registrationservice.kafka.RegistrationProducer;
import com.eventmanagement.registrationservice.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private RegistrationProducer registrationProducer;

    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @GetMapping("/event/{eventId}")
    public List<Registration> getRegistrationsByEventId(@PathVariable Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    @GetMapping("/user/{userId}")
    public List<Registration> getRegistrationsByUserId(@PathVariable Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    @PostMapping
    public Registration registerForEvent(@RequestBody Registration registration) {
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("CONFIRMED");
        Registration savedRegistration = registrationRepository.save(registration);
        
        // Produce message to Kafka
        registrationProducer.sendRegistrationEvent(savedRegistration.getEventId(), savedRegistration.getUserId());
        
        return savedRegistration;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelRegistration(@PathVariable Long id) {
        return registrationRepository.findById(id).map(registration -> {
            registrationRepository.delete(registration);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
