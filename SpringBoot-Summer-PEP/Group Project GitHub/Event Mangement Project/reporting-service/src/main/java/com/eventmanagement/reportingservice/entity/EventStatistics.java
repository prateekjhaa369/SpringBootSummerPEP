package com.eventmanagement.reportingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_statistics")
public class EventStatistics {

    @Id
    private Long eventId;
    private Long totalRegistrations;

    public EventStatistics() {
    }

    public EventStatistics(Long eventId, Long totalRegistrations) {
        this.eventId = eventId;
        this.totalRegistrations = totalRegistrations;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTotalRegistrations() {
        return totalRegistrations;
    }

    public void setTotalRegistrations(Long totalRegistrations) {
        this.totalRegistrations = totalRegistrations;
    }
}
