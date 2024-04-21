package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinobicoders.teamcodeapi.model.Notification;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return (List<Notification>) notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findNotificationByUserId(userId);
    }

    public void createNotification(Notification notification) {

        notification.setViewed(false);
        notification.setCreationDate(new Date());

        notificationRepository.save(notification);
    }

    @Transactional
    public Notification updateNotification(Long id, Notification notificationDetails) {
        Notification notification = notificationRepository.findById(id).orElse(null);

        if (notification == null)
            return null;

        notification.setMessage(notificationDetails.getMessage());
        notification.setViewed(notificationDetails.isViewed());

        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
