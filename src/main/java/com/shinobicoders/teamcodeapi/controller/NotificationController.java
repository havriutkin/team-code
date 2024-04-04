package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.Notification;
import com.shinobicoders.teamcodeapi.service.NotificationService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) throws ChangeSetPersister.NotFoundException {
        // Get User
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = userService.getUserByEmail(userDetails.getUsername()).getId();

        // Check if user is authorized to view notifications
        if (!currentUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification updatedNotification) throws ChangeSetPersister.NotFoundException {
        // Get User
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = userService.getUserByEmail(userDetails.getUsername()).getId();

        // Check if user is authorized to update notification
        if (!currentUserId.equals(updatedNotification.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Notification notification = notificationService.updateNotification(id, updatedNotification);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        // Get User
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = userService.getUserByEmail(userDetails.getUsername()).getId();

        // Check if user is authorized to delete notification
        Notification notification = notificationService.getNotificationById(id);
        if (!currentUserId.equals(notification.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
