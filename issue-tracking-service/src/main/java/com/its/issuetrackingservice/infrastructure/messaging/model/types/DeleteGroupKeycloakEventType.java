package com.its.issuetrackingservice.infrastructure.messaging.model.types;


import com.its.issuetrackingservice.infrastructure.messaging.enums.KeycloakEventType;
import com.its.issuetrackingservice.infrastructure.messaging.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;
import org.springframework.stereotype.Component;

@Component
public class DeleteGroupKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.DELETE_GROUP;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        String projectKeycloakId = getResourcePathToken(keycloakEvent,1);
        getProjectService().changeActiveStateOfUser(projectKeycloakId, Boolean.FALSE, Boolean.TRUE);
    }
}
