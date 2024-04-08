package com.its.keycloakintegrationservice.config.kafka;

import com.its.keycloakintegrationservice.model.KeycloakEvent;
import com.its.keycloakintegrationservice.util.KeycloakEventSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class KafkaProducerConfig {
    public static Properties getProperties(String bootstrapAddress) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KeycloakEventSerializer.class);

        return properties;
    }

    public static KafkaProducer<String, KeycloakEvent> kafkaProducer(String bootstrapAddress) {
        return new KafkaProducer<>(getProperties(bootstrapAddress));
    }

}
