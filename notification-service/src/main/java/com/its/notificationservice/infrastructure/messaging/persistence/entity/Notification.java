package com.its.notificationservice.infrastructure.messaging.persistence.entity;

import com.its.notificationservice.infrastructure.messaging.persistence.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity()
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Notification extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence")
    @SequenceGenerator(name = "notification_sequence", sequenceName = "notification_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @NotBlank
    @Column(name = "target", length = 64)
    private String target;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false, length = 64)
    private NotificationType notificationType;

    @Column(name = "body", nullable = false ,length = 1024)
    private String body;

    @Column(name = "subject", nullable = false, length = 128)
    private String subject;

    @Column(name = "sent", nullable = false)
    private Boolean sent;

    @Column(name = "exception_message", length = 256)
    private String exceptionMessage;

    @Column(name = "last_name", nullable = false, length = 32)
    private String serviceName;
}
