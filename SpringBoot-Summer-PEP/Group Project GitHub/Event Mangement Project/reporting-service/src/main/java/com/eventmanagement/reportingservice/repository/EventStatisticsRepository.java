package com.eventmanagement.reportingservice.repository;

import com.eventmanagement.reportingservice.entity.EventStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStatisticsRepository extends JpaRepository<EventStatistics, Long> {
}
