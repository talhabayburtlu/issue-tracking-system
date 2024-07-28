package com.its.issuetrackingservice.infrastructure.messaging.model.types;


import com.its.issuetrackingservice.infrastructure.messaging.enums.KeycloakEventType;
import com.its.issuetrackingservice.infrastructure.messaging.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.DELETE_USER;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        String userKeycloakId = getResourcePathToken(keycloakEvent,1);
        getUserService().changeActiveStateOfUser(userKeycloakId, Boolean.FALSE, Boolean.TRUE);
    }
}
