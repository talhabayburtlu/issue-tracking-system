package com.its.issuetrackingservice.event.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.InternalServerException;
import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;

import java.util.HashMap;
import java.util.Map;

public class KeycloakEventFactory {

    static Map<KeycloakEventType, AbstractKeycloakEvent> keycloakEvents = new HashMap<>();

    public static AbstractKeycloakEvent getKeycloakEvent(KeycloakEvent keycloakEvent) {
        return getKeycloakEvent(KeycloakEventType.valueOf(keycloakEvent.getOperationType()));
    }

    public static AbstractKeycloakEvent getKeycloakEvent(KeycloakEventType keycloakEventType) {
        if (!keycloakEvents.containsKey(keycloakEventType)) {
            throw new InternalServerException(I18nExceptionKeys.KEYCLOAK_EVENT_TYPE_NOT_IMPLEMENTED);
        }

        return keycloakEvents.get(keycloakEventType);
    }

    public static void register(AbstractKeycloakEvent keycloakEvent) {
        keycloakEvents.put(keycloakEvent.supportedKeycloakEventType(), keycloakEvent);
    }

}
