package com.its.keycloakintegrationservice.factory;

import com.its.keycloakintegrationservice.config.kafka.KafkaProducerConfig;
import com.its.keycloakintegrationservice.model.KeycloakEvent;
import com.its.keycloakintegrationservice.util.KeycloakUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;

@Slf4j
@RequiredArgsConstructor
public class CustomEventListenerProvider implements EventListenerProvider {
    private final KeycloakSession session;
    private final RealmProvider model;
    private KafkaProducer<String, KeycloakEvent> kafkaProducer;

    public CustomEventListenerProvider(KeycloakSession session, String bootstrapAddress) {
        this.session = session;
        this.model = session.realms();
        kafkaProducer = KafkaProducerConfig.kafkaProducer(bootstrapAddress);
    }

    @Override
    public void onEvent(Event event) {}

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        if (KeycloakUtil.isUserRelatedEvent(adminEvent)) {
            RealmModel realm = this.model.getRealm(adminEvent.getRealmId());

            KeycloakEvent keycloakEvent = KeycloakEvent.builder()
                    .userId(adminEvent.getAuthDetails().getUserId())
                    .eventId(adminEvent.getId())
                    .realmName(realm.getName())
                    .operationType(String.format("%s_%s", adminEvent.getOperationType().toString() , adminEvent.getResourceType()))
                    .payload(KeycloakUtil.extractRepresentation(adminEvent))
                    .build();

            sendKeycloakEvent(keycloakEvent);
        }
    }

    public void sendKeycloakEvent(KeycloakEvent keycloakEvent) {
        ProducerRecord<String, KeycloakEvent> producerRecord = new ProducerRecord<>(KeycloakUtil.getTopicNameByRealmName(keycloakEvent.getRealmName()), keycloakEvent);
        kafkaProducer.send(producerRecord);
    }

    @Override
    public void close() {
    }
}
