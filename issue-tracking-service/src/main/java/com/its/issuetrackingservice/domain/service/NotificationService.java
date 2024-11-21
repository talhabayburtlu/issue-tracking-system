package com.its.issuetrackingservice.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.issuetrackingservice.infrastructure.configuration.kafka.KafkaProducerConfig;
import com.its.issuetrackingservice.infrastructure.messaging.constant.KafkaConstants;
import com.its.issuetrackingservice.infrastructure.messaging.model.BulkNotificationRequest;
import com.its.issuetrackingservice.infrastructure.messaging.model.Recipient;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Participation;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ObjectMapper mapper;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.application.name}")
    private String serviceName;

    public void sendNotification(List<Participation> participants, String body, String subject) {
        List<Recipient> recipients = participants.stream()
                .map(Participation::getUser)
                .map(user -> Recipient.builder().mail(user.getEmail()).build())
                .toList();

        BulkNotificationRequest bulkNotificationRequest = BulkNotificationRequest.builder()
                .recipients(recipients)
                .body(body)
                .subject(subject)
                .serviceName(serviceName)
                .build();

        KafkaProducer<String, Object> kafkaProducer = KafkaProducerConfig.kafkaProducer(bootstrapAddress);
        Map<String, Object> message = mapper.convertValue(bulkNotificationRequest, Map.class);
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(KafkaConstants.Topics.NOTIFICATION_REQUEST_TOPIC, message);
        kafkaProducer.send(producerRecord);
    }

}
