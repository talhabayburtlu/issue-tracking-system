package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
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
        getUserDomainService().changeActiveStateOfUser(userKeycloakId, Boolean.FALSE, Boolean.TRUE);
    }
}
