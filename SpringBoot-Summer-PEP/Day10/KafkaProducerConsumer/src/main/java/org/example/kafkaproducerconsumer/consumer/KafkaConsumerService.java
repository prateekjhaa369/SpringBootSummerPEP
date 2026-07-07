package org.example.kafkaproducerconsumer.consumer;

import org.example.kafkaproducerconsumer.entity.KafkaMessage;
import org.example.kafkaproducerconsumer.repository.MessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final MessageRepository repository;

    public KafkaConsumerService(MessageRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "orders", groupId = "myGroup")
    public void consume(String message) {

        System.out.println("Consumer Received : " + message);

        KafkaMessage kafkaMessage = new KafkaMessage();

        kafkaMessage.setGroupId("myGroup");
        kafkaMessage.setMessage(message);

        repository.save(kafkaMessage);
    }
}