package com.its.notificationservice.domain;

import com.its.notificationservice.infrastructure.messaging.persistence.NotificationType;
import com.its.notificationservice.infrastructure.messaging.persistence.entity.Notification;
import com.its.notificationservice.infrastructure.messaging.persistence.repository.NotificationRepository;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String username;

    public void processEmailRequest(String to, String body, String subject, String serviceName) {
        String exceptionMessage = null;
        Boolean sent = Boolean.FALSE;

        try {
            if (!isValidEmailAddress(to)) {
                throw new IllegalArgumentException("Email is not valid");
            }

            if (StringUtils.isEmpty(body) || StringUtils.isEmpty(subject)) {
                throw new IllegalArgumentException("Body and subject must not be empty!");
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mimeMessage);
            sent = Boolean.TRUE;
        } catch (Exception e) {
            // TODO: Log
            exceptionMessage = e.getMessage();
        } finally {
            Notification notification = Notification.builder()
                    .target(to)
                    .body(body)
                    .subject(subject)
                    .sent(sent)
                    .exceptionMessage(exceptionMessage)
                    .serviceName(serviceName)
                    .notificationType(NotificationType.EMAIL)
                    .build();
            notification.setAuditableFields();
            notificationRepository.save(notification);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
