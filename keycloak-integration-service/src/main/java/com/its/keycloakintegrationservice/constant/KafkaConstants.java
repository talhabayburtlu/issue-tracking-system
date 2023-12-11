package com.its.keycloakintegrationservice.constant;

public class KafkaConstants {
    public static final String KEYCLOAK_CONSUMER_GROUP_ID = "keycloak.consumer.group.id";
    public static class Topics {
        public static final String USER_EVENTS_TOPIC_NAME = "keycloak_user_events_topic";
        public static final String ADMIN_EVENTS_TOPIC_NAME = "keycloak_admin_events_topic";
    }

}
