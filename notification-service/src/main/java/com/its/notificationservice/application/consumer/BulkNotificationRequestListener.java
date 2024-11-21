package com.its.notificationservice.application.consumer;

import com.its.notificationservice.domain.NotificationService;
import com.its.notificationservice.infrastructure.messaging.model.BulkNotificationRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BulkNotificationRequestListener implements MessageListener<String, BulkNotificationRequest> {

    private final NotificationService notificationService;

    @Override
    @Transactional
    public void onMessage(ConsumerRecord<String, BulkNotificationRequest> consumerRecord) {
        BulkNotificationRequest bulkNotificationRequest = consumerRecord.value();
        notificationService.processBulkNotificationRequest(bulkNotificationRequest);
    }

    @Override
    public void onMessage(ConsumerRecord<String, BulkNotificationRequest> data, Acknowledgment acknowledgment) {
        MessageListener.super.onMessage(data, acknowledgment);
    }

    @Override
    public void onMessage(ConsumerRecord<String, BulkNotificationRequest> data, Consumer<?, ?> consumer) {
        MessageListener.super.onMessage(data, consumer);
    }

    @Override
    public void onMessage(ConsumerRecord<String, BulkNotificationRequest> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        MessageListener.super.onMessage(data, acknowledgment, consumer);
    }
}
