package com.eventmanagement.reportingservice.kafka;

import com.eventmanagement.reportingservice.entity.EventStatistics;
import com.eventmanagement.reportingservice.repository.EventStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RegistrationConsumer {

    @Autowired
    private EventStatisticsRepository eventStatisticsRepository;

    @KafkaListener(topics = "registration_events", groupId = "reporting-group")
    public void consumeRegistrationEvent(String message) {
        System.out.println("Consumed Kafka message -> " + message);
        
        try {
            // Expected format: eventId:userId
            String[] parts = message.split(":");
            if (parts.length == 2) {
                Long eventId = Long.parseLong(parts[0]);
                
                EventStatistics stats = eventStatisticsRepository.findById(eventId)
                        .orElse(new EventStatistics(eventId, 0L));
                
                stats.setTotalRegistrations(stats.getTotalRegistrations() + 1);
                eventStatisticsRepository.save(stats);
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
