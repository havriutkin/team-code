package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinobicoders.teamcodeapi.model.Notification

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return (List<Notification>) notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findNotificationByUserId(userId);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long id, Notification notificationDetails) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));

        notification.setMessage(notificationDetails.getMessage());
        notification.setViewed(notificationDetails.isViewed());
        notification.setCreationDate(notificationDetails.getCreationDate());
        notification.setUser(notificationDetails.getUser());

        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
