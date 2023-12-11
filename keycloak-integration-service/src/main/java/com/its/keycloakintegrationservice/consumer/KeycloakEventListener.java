package com.its.keycloakintegrationservice.consumer;

import com.its.keycloakintegrationservice.constant.KafkaConstants;
import com.its.keycloakintegrationservice.service.KeycloakEventService;
import lombok.RequiredArgsConstructor;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakEventListener {
    private final KeycloakEventService keycloakEventService;

    @KafkaListener(topics = KafkaConstants.Topics.USER_EVENTS_TOPIC_NAME)
    public void onUserKafkaEventReceived(Event userEvent) {
        keycloakEventService.consumeUserEvent(userEvent);
    }

    @KafkaListener(topics = KafkaConstants.Topics.ADMIN_EVENTS_TOPIC_NAME)
    public void onAdminKafkaEventReceived(AdminEvent adminEvent) {
        keycloakEventService.consumeAdminEvent(adminEvent);
    }

}
