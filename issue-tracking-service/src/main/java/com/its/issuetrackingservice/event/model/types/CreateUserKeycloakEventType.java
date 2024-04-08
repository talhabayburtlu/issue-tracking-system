package com.its.issuetrackingservice.event.model.types;


import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.model.AbstractKeycloakEvent;
import com.its.issuetrackingservice.event.model.KeycloakEvent;
import com.its.issuetrackingservice.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

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

        user.setKeycloakId(keycloakEvent.getUserId());
        user.setIsActive(Boolean.TRUE);


        getUserDomainService().upsertUser(user, Boolean.TRUE);
    }
}
