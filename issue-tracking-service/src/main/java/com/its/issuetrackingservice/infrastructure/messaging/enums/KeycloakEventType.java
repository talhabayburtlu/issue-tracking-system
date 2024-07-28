package com.its.issuetrackingservice.infrastructure.messaging.enums;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.InternalServerException;

import java.util.Arrays;
import java.util.Objects;

public enum KeycloakEventType {
    CREATE_USER,
    UPDATE_USER,
    DELETE_USER,
    CREATE_GROUP,
    UPDATE_GROUP,
    DELETE_GROUP,
    CREATE_GROUP_MEMBERSHIP,
    DELETE_GROUP_MEMBERSHIP
    ;

    public static KeycloakEventType getKeycloakEventTypeByValue(String type, boolean isRequired) {
        KeycloakEventType keycloakEventType = Arrays.stream(values()).filter(eventType -> eventType.name().equals(type)).findFirst().orElse(null);
        if (isRequired && Objects.isNull(keycloakEventType)) {
            throw new InternalServerException(I18nExceptionKeys.KEYCLOAK_EVENT_TYPE_NOT_IMPLEMENTED);
        }

        return keycloakEventType;
    }
}
