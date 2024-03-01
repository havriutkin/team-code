package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findNotificationByUserId(Long userId);
}
