package com.its.notificationservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.notificationservice.infrastructure.messaging.model.BulkNotificationRequest;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class BulkNotificationRequestDeserializer implements Deserializer<BulkNotificationRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public BulkNotificationRequest deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, BulkNotificationRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BulkNotificationRequest deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
