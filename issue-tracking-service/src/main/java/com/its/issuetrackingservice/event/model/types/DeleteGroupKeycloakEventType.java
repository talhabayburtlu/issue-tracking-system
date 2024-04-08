package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeleteGroupKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.DELETE_GROUP;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        Map<String, Object> groupMap = keycloakEvent.getPayload();
        getProjectDomainService().changeActiveStateOfUser((String) groupMap.get("id"), Boolean.FALSE, Boolean.TRUE);
    }
}
