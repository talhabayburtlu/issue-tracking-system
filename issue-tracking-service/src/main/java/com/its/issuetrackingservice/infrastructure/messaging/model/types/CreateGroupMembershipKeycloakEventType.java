package com.its.issuetrackingservice.infrastructure.messaging.model.types;


import com.its.issuetrackingservice.infrastructure.messaging.enums.KeycloakEventType;
import com.its.issuetrackingservice.infrastructure.messaging.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Membership;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
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

        User user = getUserService().getUserByKeycloakId(userKeycloakId, false);
        Project project = getProjectService().getProjectByKeycloakId(projectKeycloakId, false);

        if (Objects.isNull(user) || Objects.isNull(project)) {
            return;
        }

        Membership membership =Membership.builder()
                .project(project)
                .user(user)
                .build();
        membership.setAuditableFields(null);
        user.getMemberships().add(membership);

        getUserService().upsertUser(user, Boolean.TRUE);
    }
}
