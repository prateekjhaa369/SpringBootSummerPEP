package org.example.kafkaproducerconsumer.entity;

import jakarta.persistence.*;

@Entity
public class KafkaMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupId;

    private String message;

    public KafkaMessage() {
    }

    public KafkaMessage(String groupId, String message) {
        this.groupId = groupId;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}