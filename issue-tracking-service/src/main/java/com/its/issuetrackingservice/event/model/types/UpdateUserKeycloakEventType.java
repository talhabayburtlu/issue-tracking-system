package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
import com.its.issuetrackingservice.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class UpdateUserKeycloakEventType extends AbstractKeycloakEvent {

    @Override
    public KeycloakEventType supportedKeycloakEventType() {
        return KeycloakEventType.UPDATE_USER;
    }

    @Override
    public void processEvent(KeycloakEvent keycloakEvent) {
        User user = getUserDomainService().getUserByKeycloakId(keycloakEvent.getUserId(), false);
        if (Objects.isNull(user)) {
            user = new User();
            user.setIsActive(Boolean.TRUE);
        }

        Map<String, Object> userMap = keycloakEvent.getPayload();
        if (userMap.containsKey("username")) {
            user.setUsername((String) userMap.get("username"));
        }

        if (userMap.containsKey("firstName")) {
            user.setUsername((String) userMap.get("firstName"));
        }

        if (userMap.containsKey("lastName")) {
            user.setUsername((String) userMap.get("lastName"));
        }

        if (userMap.containsKey("email")) {
            user.setUsername((String) userMap.get("email"));
        }

        getUserDomainService().upsertUser(user, Boolean.TRUE);
    }
}
