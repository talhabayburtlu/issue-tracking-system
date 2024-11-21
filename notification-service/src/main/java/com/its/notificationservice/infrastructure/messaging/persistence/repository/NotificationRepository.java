package com.its.notificationservice.infrastructure.messaging.persistence.repository;

import com.its.notificationservice.infrastructure.messaging.persistence.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
