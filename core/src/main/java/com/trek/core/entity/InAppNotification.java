package com.trek.core.entity;

import com.trek.core.constants.NotificationEntityType;
import com.trek.core.constants.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "in_app_notification", indexes = {
        @Index(name = "idx_notif_receiver_read", columnList = "receiver_id, is_read"),
        @Index(name = "idx_notif_receiver_created", columnList = "receiver_id, created_at")
})
public class InAppNotification extends BaseEntity {

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Trekker receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Trekker actor;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private NotificationEntityType entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}