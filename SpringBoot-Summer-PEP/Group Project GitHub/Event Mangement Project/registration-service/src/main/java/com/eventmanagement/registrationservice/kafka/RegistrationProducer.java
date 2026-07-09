package com.eventmanagement.registrationservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RegistrationProducer {

    private static final String TOPIC = "registration_events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendRegistrationEvent(Long eventId, Long userId) {
        String message = eventId + ":" + userId;
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Produced Kafka message -> " + message);
    }
}
