package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
import com.its.issuetrackingservice.persistence.entity.Project;
import com.its.issuetrackingservice.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class CreateUserKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.CREATE_USER;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        Map<String, Object> userMap = keycloakEvent.getPayload();
        User user = getUserDomainService().getUserByUsername((String) userMap.get("username"), Boolean.FALSE);
        if (Objects.isNull(user)) {
            user = User.builder()
                    .username((String) userMap.get("username"))
                    .firstName((String) userMap.get("firstName"))
                    .lastName((String) userMap.get("lastName"))
                    .email((String) userMap.get("email"))
                    .build();
        }

        String keycloakId = getResourcePathToken(keycloakEvent,1);
        user.setKeycloakId(keycloakId);
        user.setIsActive(Boolean.TRUE);

        List<String> projectNames = ((List<String>) userMap.get("groups")).stream().map(group -> group.substring(1)).toList();
        Set<Project> addedProjects = getProjectDomainService().getProjectsByNames(projectNames);
        user.setProjects(addedProjects);

        getUserDomainService().upsertUser(user, Boolean.TRUE);
    }
}
