package com.its.issuetrackingservice.event.consumer;

import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
import com.its.issuetrackingservice.event.service.KeycloakEventFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeycloakEventListener implements MessageListener<String, KeycloakEvent> {

    @Override
    @Transactional
    public void onMessage(ConsumerRecord<String, KeycloakEvent> record) {
        KeycloakEvent event = record.value();
        AbstractKeycloakEvent keycloakEvent = KeycloakEventFactory.getKeycloakEvent(event, Boolean.FALSE);
        if (Objects.nonNull(keycloakEvent)) {
            keycloakEvent.processEvent(event);
        }
    }

    @Override
    public void onMessage(ConsumerRecord<String, KeycloakEvent> data, Acknowledgment acknowledgment) {
        MessageListener.super.onMessage(data, acknowledgment);
    }

    @Override
    public void onMessage(ConsumerRecord<String, KeycloakEvent> data, Consumer<?, ?> consumer) {
        MessageListener.super.onMessage(data, consumer);
    }

    @Override
    public void onMessage(ConsumerRecord<String, KeycloakEvent> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        MessageListener.super.onMessage(data, acknowledgment, consumer);
    }
}
