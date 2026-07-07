package org.example.kafkaproducerconsumer.repository;

import org.example.kafkaproducerconsumer.entity.KafkaMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<KafkaMessage, Long> {

}