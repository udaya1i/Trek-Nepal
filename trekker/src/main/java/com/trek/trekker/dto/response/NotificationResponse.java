package com.trek.trekker.dto.response;

import com.trek.core.constants.NotificationEntityType;
import com.trek.core.constants.NotificationType;
import com.trek.core.entity.InAppNotification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponse {

    private Long id;
    private String content;
    private NotificationType notificationType;
    private NotificationEntityType entityType;
    private Long entityId;
    private Long actorId;
    private String actorName;
    private Boolean isRead;
    private LocalDateTime createdAt;

    public static NotificationResponse from(InAppNotification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .content(notification.getContent())
                .notificationType(notification.getNotificationType())
                .entityType(notification.getEntityType())
                .entityId(notification.getEntityId())
                .actorId(notification.getActor() != null ? notification.getActor().getId() : null)
                .actorName(notification.getActor() != null ? notification.getActor().getFullName() : null)
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}