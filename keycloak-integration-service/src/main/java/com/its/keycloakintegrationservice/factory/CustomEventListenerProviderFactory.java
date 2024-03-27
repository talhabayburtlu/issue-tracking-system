package com.its.keycloakintegrationservice.factory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

@Getter
@Slf4j
public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {
    private static final String PROVIDER_ID = "custom-event-listener";

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        String bootstrapAddress = System.getenv("KAFKA_BOOTSTRAP_ADDRESS");
        return new CustomEventListenerProvider(keycloakSession, bootstrapAddress);
    }

    @Override
    public void init(Config.Scope scope) {
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

}
