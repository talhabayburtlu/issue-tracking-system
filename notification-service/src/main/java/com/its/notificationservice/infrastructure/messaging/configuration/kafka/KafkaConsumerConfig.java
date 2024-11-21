package com.its.notificationservice.infrastructure.messaging.configuration.kafka;

import com.its.notificationservice.application.consumer.BulkNotificationRequestListener;
import com.its.notificationservice.infrastructure.messaging.BulkNotificationRequestDeserializer;
import com.its.notificationservice.infrastructure.messaging.constant.KafkaConstants;
import com.its.notificationservice.infrastructure.messaging.model.BulkNotificationRequest;
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
import java.util.List;
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
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.NOTIFICATION_CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BulkNotificationRequestDeserializer.class);
        return props;
    }

    @Bean
    public KafkaMessageListenerContainer<String, BulkNotificationRequest> kafkaListenerContainerFactory() {
        ContainerProperties containerProps = new ContainerProperties(getTopicList());
        containerProps.setMessageListener(context.getBean(BulkNotificationRequestListener.class));

        DefaultKafkaConsumerFactory<String, BulkNotificationRequest> cf = new DefaultKafkaConsumerFactory<>(consumerProps());
        return new KafkaMessageListenerContainer<>(cf, containerProps);
    }

    @Bean
    private static String[] getTopicList() {
        return List.of(KafkaConstants.Topics.NOTIFICATION_REQUEST_TOPIC).toArray(new String[0]);
    }
}