package com.its.keycloakintegrationservice;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class CustomEventListenerProviderFactory implements EventListenerProviderFactory, ServerInfoAwareProviderFactory {

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        log.info("EVENT LISTENER REGISTERED 1");
        return new CustomEventListenerKafkaProvider(keycloakSession);
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
        return "custom-event-listener";
    }

    @Override
    public Map<String, String> getOperationalInfo() {
        log.info("EVENT LISTENER REGISTERED 3");
        Map<String, String> ret = new LinkedHashMap<>();
        ret.put("version", "5.0");
        return ret;
    }
}
