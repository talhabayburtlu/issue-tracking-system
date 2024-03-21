package com.its.keycloakintegrationservice.factory;

import com.its.keycloakintegrationservice.util.KeycloakUtil;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;
@Slf4j
public class CustomEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;
    private final RealmProvider model;

    public CustomEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
        log.info("EVENT ARRIVED 1");
        if (KeycloakUtil.isUserRelatedEvent(event)) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel newRegisteredUser = this.session.users().getUserById(realm, event.getUserId());
            // kafkaTemplate.send(KafkaConstants.Topics.USER_EVENTS_TOPIC_NAME, event.getUserId(), event);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        log.info("EVENT ARRIVED 2");
        if (KeycloakUtil.isUserRelatedEvent(adminEvent)) {
            // kafkaTemplate.send(KafkaConstants.Topics.USER_EVENTS_TOPIC_NAME, adminEvent.getId(), adminEvent);
        }
    }

    @Override
    public void close() {
    }
}
