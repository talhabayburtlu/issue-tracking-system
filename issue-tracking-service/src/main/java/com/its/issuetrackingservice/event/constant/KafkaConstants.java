package com.its.issuetrackingservice.event.constant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KafkaConstants {
    public static final String KEYCLOAK_CONSUMER_GROUP_ID = "keycloak.consumer.group.id";
    public static class Topics {
        public static final String KEYCLOAK_EVENT_TOPIC_PREFIX = "keycloak_event_topic";

        public static final Set<String> RELATED_REALM_NAMES = new HashSet<>(List.of("its-realm"));
    }

}
