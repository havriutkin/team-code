package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
