package com.its.issuetrackingservice.infrastructure.configuration.kafka;

import com.its.issuetrackingservice.application.consumer.KeycloakEventListener;
import com.its.issuetrackingservice.infrastructure.messaging.UserEventDeserializer;
import com.its.issuetrackingservice.infrastructure.messaging.constant.KafkaConstants;
import com.its.issuetrackingservice.infrastructure.messaging.model.KeycloakEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    private final ApplicationContext context;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    public KafkaConsumerConfig(ApplicationContext context) {
        this.context = context;
    }

    public Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.KEYCLOAK_CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserEventDeserializer.class);
        return props;
    }

    @Bean
    public KafkaMessageListenerContainer<String, KeycloakEvent> kafkaListenerContainerFactory() {
        ContainerProperties containerProps = new ContainerProperties(getTopicList());
        containerProps.setMessageListener(context.getBean(KeycloakEventListener.class));

        DefaultKafkaConsumerFactory<String, KeycloakEvent> cf = new DefaultKafkaConsumerFactory<>(consumerProps());
        return new KafkaMessageListenerContainer<>(cf, containerProps);
    }

    @Bean
    private static String[] getTopicList() {
        return KafkaConstants.Topics.RELATED_REALM_NAMES.stream()
                .map(realmName -> String.format("%s_%s", KafkaConstants.Topics.KEYCLOAK_EVENT_TOPIC_PREFIX, realmName))
                .toArray(String[]::new);
    }
}