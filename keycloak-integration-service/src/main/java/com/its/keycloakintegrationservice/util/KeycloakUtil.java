package com.its.keycloakintegrationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.keycloakintegrationservice.constant.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.ResourceType;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KeycloakUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static boolean isUserRelatedEvent(Object event) {
        if (event instanceof AdminEvent adminEvent) {
            if (adminEvent.getResourceType().equals(ResourceType.USER) || adminEvent.getResourceType().equals(ResourceType.GROUP)) {
                return switch (adminEvent.getOperationType()) {
                    case CREATE, UPDATE, DELETE -> true;
                    default -> false;
                };
            }
        }
        return false;
    }

    public static Map<String, Object> extractRepresentation(AdminEvent adminEvent) {
        if (adminEvent.getRepresentation() == null) {
            return new HashMap<>();
        }

        Map<String, Object> representationMap = new HashMap<>();
        try {
            representationMap = mapper.readValue(adminEvent.getRepresentation(), new TypeReference<>() {
            });
            return representationMap;
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return representationMap;
    }

    public static String getTopicNameByRealmName(String realmName) {
        return String.format("%s_%s" , KafkaConstants.Topics.KEYCLOAK_EVENT_TOPIC_PREFIX, realmName);
    }
}
