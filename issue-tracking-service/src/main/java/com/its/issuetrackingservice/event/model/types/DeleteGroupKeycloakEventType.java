package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
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
        getProjectDomainService().changeActiveStateOfUser(projectKeycloakId, Boolean.FALSE, Boolean.TRUE);
    }
}
