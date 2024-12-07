package com.io.github.com.mkmuniz.flowmanager.core.ports.in;

import java.util.List;

import com.io.github.com.mkmuniz.flowmanager.core.domain.Notification;

public interface NotificationServcePort {
    Notification createNotification(Notification notification);

    List<Notification> getAllNotifications();

    Notification getNotificationById(Long id);

    Notification updateNotification(Long id, Notification notification);

    Notification updateNotificationStatus(Long id, String status);

    Notification deleteNotification(Long id);
}
