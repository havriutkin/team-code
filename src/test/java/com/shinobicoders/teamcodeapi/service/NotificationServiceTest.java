package com.shinobicoders.teamcodeapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.shinobicoders.teamcodeapi.repository.NotificationRepository;
import com.shinobicoders.teamcodeapi.model.Notification;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    private Notification notificationMock;

    @BeforeEach
    void setUp() {
        notificationMock = new Notification();
        notificationMock.setId(1L);
        notificationMock.setMessage("Test message");
        notificationMock.setViewed(false);
        notificationMock.setCreationDate(new Date());
    }

    @Nested
    class GetAllNotificationsTest {
        @Test
        void shouldReturnAllNotifications() {
            List<Notification> notifications = List.of(notificationMock);
            when(notificationRepository.findAll()).thenReturn(notifications);

            List<Notification> result = notificationService.getAllNotifications();

            assertEquals(1, result.size());
            assertEquals(notificationMock, result.get(0));
            verify(notificationRepository, times(1)).findAll();
        }

        @Test
        void shouldReturnEmptyList() {
            List<Notification> notifications = List.of();
            when(notificationRepository.findAll()).thenReturn(notifications);

            List<Notification> result = notificationService.getAllNotifications();

            assertEquals(0, result.size());
            verify(notificationRepository, times(1)).findAll();
        }
    }

    @Nested
    class GetNotificationByIdTest {
        @Test
        void shouldReturnNotificationById() {
            when(notificationRepository.findById(1L)).thenReturn(java.util.Optional.of(notificationMock));

            Notification result = notificationService.getNotificationById(1L);

            assertEquals(notificationMock, result);
            verify(notificationRepository, times(1)).findById(1L);
        }

        @Test
        void shouldReturnNull() {
            when(notificationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

            Notification result = notificationService.getNotificationById(1L);

            assertNull(result);
            verify(notificationRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class CreateNotificationTest {
        @Test
        void shouldCreateNotification() {
            notificationService.createNotification(notificationMock);

            verify(notificationRepository, times(1)).save(notificationMock);
        }
    }

    @Nested
    class UpdateNotificationTest {
        @Test
        void shouldUpdateNotification() {
            Notification updatedNotification = new Notification();
            updatedNotification.setViewed(true);
            when(notificationRepository.findById(1L)).thenReturn(java.util.Optional.of(notificationMock));
            when(notificationRepository.save(notificationMock)).thenReturn(updatedNotification);

            Notification result = notificationService.updateNotification(1L, updatedNotification);

            assertEquals(updatedNotification.isViewed(), result.isViewed());
            verify(notificationRepository, times(1)).findById(1L);
            verify(notificationRepository, times(1)).save(notificationMock);
        }

        @Test
        void shouldReturnNull() {
            when(notificationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

            Notification updatedNotification = new Notification();
            updatedNotification.setMessage("Updated message");
            updatedNotification.setViewed(true);

            Notification result = notificationService.updateNotification(1L, updatedNotification);

            assertNull(result);
            verify(notificationRepository, times(1)).findById(1L);
            verify(notificationRepository, never()).save(notificationMock);
        }
    }

    @Nested
    class DeleteNotificationTest {
        @Test
        void shouldDeleteNotification() {
            notificationService.deleteNotification(1L);

            verify(notificationRepository, times(1)).deleteById(1L);
        }
    }
}
