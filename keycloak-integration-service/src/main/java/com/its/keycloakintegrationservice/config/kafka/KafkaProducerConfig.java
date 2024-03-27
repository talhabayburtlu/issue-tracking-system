package com.its.keycloakintegrationservice.config.kafka;

import com.its.keycloakintegrationservice.model.UserEvent;
import com.its.keycloakintegrationservice.util.UserEventSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class KafkaProducerConfig {
    public static Properties getProperties(String bootstrapAddress) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserEventSerializer.class);

        return properties;
    }

    public static KafkaProducer<String, UserEvent> kafkaProducer(String bootstrapAddress) {
        return new KafkaProducer<>(getProperties(bootstrapAddress));
    }

}
