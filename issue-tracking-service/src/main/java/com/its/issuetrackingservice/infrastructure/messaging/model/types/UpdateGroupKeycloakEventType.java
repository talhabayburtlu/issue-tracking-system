package com.its.issuetrackingservice.infrastructure.messaging.model.types;


import com.its.issuetrackingservice.infrastructure.messaging.enums.KeycloakEventType;
import com.its.issuetrackingservice.infrastructure.messaging.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UpdateGroupKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.UPDATE_GROUP;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        Map<String, Object> groupMap = keycloakEvent.getPayload();
        Project project = getProjectService().getProjectByName((String) groupMap.get("name"), Boolean.FALSE);
        if (Objects.isNull(project)) {
            project = new Project();
            project.setKeycloakId((String) groupMap.get("id"));
            project.setIsActive(Boolean.TRUE);
        }

        if (groupMap.containsKey("name")) {
            project.setName((String) groupMap.get("name"));
        }

        if (groupMap.containsKey("attributes")) {
            Map<String, Object> attributes = (Map<String, Object>) groupMap.get("attributes");
            if (attributes.containsKey("abbreviation")) {
                List<String> abbreviationAttributes = (List<String>) attributes.get("abbreviation");
                project.setAbbreviation(abbreviationAttributes.getFirst());
            }
        }


        getProjectService().upsertProject(project, Boolean.TRUE);
    }
}
