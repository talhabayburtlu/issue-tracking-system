package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
import com.its.issuetrackingservice.persistence.entity.Project;
import com.its.issuetrackingservice.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateGroupMembershipKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.CREATE_GROUP_MEMBERSHIP;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        String userKeycloakId = getResourcePathToken(keycloakEvent,1);
        String projectKeycloakId = getResourcePathToken(keycloakEvent,3);

        User user = getUserDomainService().getUserByKeycloakId(userKeycloakId, false);
        Project project = getProjectDomainService().getProjectByKeycloakId(projectKeycloakId, false);

        if (Objects.isNull(user) || Objects.isNull(project)) {
            return;
        }

        user.getProjects().add(project);
        getUserDomainService().upsertUser(user, Boolean.TRUE);
    }
}
