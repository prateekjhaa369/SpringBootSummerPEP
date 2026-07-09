package com.eventmanagement.reportingservice.controller;

import com.eventmanagement.reportingservice.entity.EventStatistics;
import com.eventmanagement.reportingservice.repository.EventStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    @Autowired
    private EventStatisticsRepository eventStatisticsRepository;

    @GetMapping("/statistics")
    public List<EventStatistics> getAllStatistics() {
        return eventStatisticsRepository.findAll();
    }

    @GetMapping("/statistics/{eventId}")
    public ResponseEntity<EventStatistics> getStatisticsByEventId(@PathVariable Long eventId) {
        return eventStatisticsRepository.findById(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
