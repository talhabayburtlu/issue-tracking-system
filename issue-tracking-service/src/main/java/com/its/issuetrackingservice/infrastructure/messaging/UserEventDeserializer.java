package com.its.issuetrackingservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class UserEventDeserializer implements Deserializer<KeycloakEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public KeycloakEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, KeycloakEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KeycloakEvent deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
