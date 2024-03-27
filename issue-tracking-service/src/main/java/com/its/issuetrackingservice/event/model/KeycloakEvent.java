package com.its.issuetrackingservice.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakEvent {
    private String userId;
    private String eventId;
    private String realmName;
    private String operationType;
    private Map<String, Object> user;
}
