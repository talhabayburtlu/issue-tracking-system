package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
import com.its.issuetrackingservice.persistence.entity.Project;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class CreateGroupKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.CREATE_GROUP;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        Map<String, Object> groupMap = keycloakEvent.getPayload();
        Project project = getProjectDomainService().getProjectByName((String) groupMap.get("name"), Boolean.FALSE);
        if (Objects.isNull(project)) {
            project = Project.builder()
                    .name((String) groupMap.get("name"))
                    .build();
        }

        project.setKeycloakId((String) groupMap.get("id"));
        project.setIsActive(Boolean.TRUE);


        getProjectDomainService().upsertProject(project, Boolean.TRUE);
    }
}
