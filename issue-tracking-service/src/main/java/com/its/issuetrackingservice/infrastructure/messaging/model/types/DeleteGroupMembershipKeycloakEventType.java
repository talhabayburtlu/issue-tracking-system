package com.its.issuetrackingservice.infrastructure.messaging.model.types;


import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.InternalServerException;
import com.its.issuetrackingservice.infrastructure.messaging.enums.KeycloakEventType;
import com.its.issuetrackingservice.infrastructure.messaging.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Membership;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class DeleteGroupMembershipKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.DELETE_GROUP_MEMBERSHIP;
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

        Optional<Membership> membershipOptional = getMembershipService().getMembershipByProjectAndUserId(user.getId(), project.getId());
        if (membershipOptional.isEmpty()) {
            throw new InternalServerException(I18nExceptionKeys.KEYCLOAK_GROUP_MEMBERSHIP_NOT_FOUND);
        }

        getMembershipService().deleteMembership(membershipOptional.get());
    }
}
