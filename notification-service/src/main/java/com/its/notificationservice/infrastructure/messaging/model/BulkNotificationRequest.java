package com.its.notificationservice.infrastructure.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkNotificationRequest {
    private List<Recipient> recipients;
    private String body;
    private String subject;
    private String serviceName;
}
