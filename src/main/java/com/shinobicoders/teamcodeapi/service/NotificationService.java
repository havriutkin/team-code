package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.exception.EntityCreationException;
import com.shinobicoders.teamcodeapi.exception.EntityDeletionException;
import com.shinobicoders.teamcodeapi.exception.EntityUpdateException;
import com.shinobicoders.teamcodeapi.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.shinobicoders.teamcodeapi.model.Notification;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return (List<Notification>) notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) throws NotFoundException
    {
        return notificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findNotificationByUserId(userId);
    }

    public Notification createNotification(Notification notification) {
        try {
            return notificationRepository.save(notification);
        } catch (Exception e){
            throw new EntityCreationException("Error creating of notification:  " + e.getMessage(), e.getCause());
        }
    }

    public Notification updateNotification(Long id, Notification notificationDetails) throws NotFoundException
    {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        notification.setMessage(notificationDetails.getMessage());
        notification.setViewed(notificationDetails.isViewed());
        notification.setCreationDate(notificationDetails.getCreationDate());
        notification.setUser(notificationDetails.getUser());

        try {
            return notificationRepository.save(notification);
        } catch (Exception e){
            throw new EntityUpdateException("Error updating notification: " + e.getMessage(), e.getCause());
        }
    }

    public void deleteNotification(Long id) {
        try{
            notificationRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityDeletionException("Error deleting notification: " + e.getMessage(), e.getCause());
        }
    }
}
