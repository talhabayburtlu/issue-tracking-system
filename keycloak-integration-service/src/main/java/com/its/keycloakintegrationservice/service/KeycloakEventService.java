package com.its.keycloakintegrationservice.service;

import com.its.keycloakintegrationservice.constant.KeycloakConstants;
import com.its.keycloakintegrationservice.util.KeycloakUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.ResourceType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakEventService {
    public void consumeUserEvent(Event userEvent) {
        if (!KeycloakUtil.isUserRelatedEvent(userEvent)) {
            return;
        }

        Map<String, String> details = userEvent.getDetails();
        switch (userEvent.getType()) {

        }
    }

    public void consumeAdminEvent(AdminEvent adminEvent) {
        if (!KeycloakUtil.isUserRelatedEvent(adminEvent)) {
            return;
        }

        Map<String, Object> representationMap = KeycloakUtil.extractRepresentation(adminEvent);
        representationMap.put(KeycloakConstants.KEYCLOAK_ID, adminEvent.getId());

        if (adminEvent.getResourceType().equals(ResourceType.USER)) {
            switch (adminEvent.getOperationType()) {
                case CREATE:
                    break;
                case UPDATE:
                    break;
                case DELETE:
                    break;
            }
        }
    }

}
