package com.its.notificationservice.domain;

import com.its.notificationservice.infrastructure.messaging.model.BulkNotificationRequest;
import com.its.notificationservice.infrastructure.messaging.model.Recipient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;

    public void processBulkNotificationRequest(BulkNotificationRequest bulkNotificationRequest) {
        for (Recipient recipient : bulkNotificationRequest.getRecipients()) {
            emailService.processEmailRequest(recipient.getMail(), bulkNotificationRequest.getBody(),
                    bulkNotificationRequest.getSubject(), bulkNotificationRequest.getServiceName());
        }
    }

}
