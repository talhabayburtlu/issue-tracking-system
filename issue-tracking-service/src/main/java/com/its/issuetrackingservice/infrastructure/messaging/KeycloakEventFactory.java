package com.its.issuetrackingservice.infrastructure.messaging;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.InternalServerException;
import com.its.issuetrackingservice.infrastructure.messaging.enums.KeycloakEventType;
import com.its.issuetrackingservice.infrastructure.messaging.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;

import java.util.HashMap;
import java.util.Map;

public class KeycloakEventFactory {

    static Map<KeycloakEventType, AbstractKeycloakEvent> keycloakEvents = new HashMap<>();

    public static AbstractKeycloakEvent getKeycloakEvent(KeycloakEvent keycloakEvent, boolean reqired) {
        return getKeycloakEvent(KeycloakEventType.getKeycloakEventTypeByValue(keycloakEvent.getOperationType(), reqired), reqired);
    }

    public static AbstractKeycloakEvent getKeycloakEvent(KeycloakEventType keycloakEventType, boolean required) {
        if (!keycloakEvents.containsKey(keycloakEventType) && required) {
            throw new InternalServerException(I18nExceptionKeys.KEYCLOAK_EVENT_TYPE_NOT_IMPLEMENTED);
        }

        return keycloakEvents.get(keycloakEventType);
    }

    public static void register(AbstractKeycloakEvent keycloakEvent) {
        keycloakEvents.put(keycloakEvent.supportedKeycloakEventType(), keycloakEvent);
    }

}
