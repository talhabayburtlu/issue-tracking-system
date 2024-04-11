package com.its.issuetrackingservice.event.model;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.InternalServerException;
import com.its.issuetrackingservice.domain.service.ProjectDomainService;
import com.its.issuetrackingservice.domain.service.UserDomainService;
import com.its.issuetrackingservice.event.enums.KeycloakEventType;
import com.its.issuetrackingservice.event.service.KeycloakEventFactory;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public abstract class AbstractKeycloakEvent {

    @Setter(onMethod_ = {@Autowired})
    private UserDomainService userDomainService;

    @Setter(onMethod_ = {@Autowired})
    private ProjectDomainService projectDomainService;

    public abstract KeycloakEventType supportedKeycloakEventType();
    public abstract void processEvent(KeycloakEvent keycloakEvent);

    @PostConstruct
    public void initialize() {
        KeycloakEventFactory.register(this);
    }

    public String getResourcePathToken(KeycloakEvent keycloakEvent, Integer tokenIndex) {
        String[] tokens = keycloakEvent.getResourcePath().split("/");

        if (tokens.length <= tokenIndex) {
            throw new InternalServerException(I18nExceptionKeys.KEYCLOAK_EVENT_RESOURCE_PATH_TOKEN_SIZE_EXCEEDED,
                    String.format("token size: %d, given token index: %d", tokens.length, tokenIndex));
        }

        return tokens[tokenIndex];
    }
}
