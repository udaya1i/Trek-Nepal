package com.trek.trekker.service;

import com.trek.core.constants.NotificationEntityType;
import com.trek.core.constants.NotificationType;
import com.trek.core.entity.InAppNotification;
import com.trek.core.entity.Trekker;
import com.trek.core.repository.InAppNotificationRepository;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InAppNotificationService {

    private final InAppNotificationRepository notificationRepository;
    private final TrekkerSecurityContextHolderHolder securityContextHolderHolder;

    public InAppNotification createNotification(Trekker receiver,
                                                Trekker actor,
                                                NotificationType type,
                                                NotificationEntityType entityType,
                                                Long entityId,
                                                String content) {

        if (receiver.getId().equals(actor.getId())) {
            return null;
        }

        InAppNotification notification = new InAppNotification();
        notification.setReceiver(receiver);
        notification.setActor(actor);
        notification.setNotificationType(type);
        notification.setEntityType(entityType);
        notification.setEntityId(entityId);
        notification.setContent(content);
        notification.setIsRead(false);

        return notificationRepository.save(notification);
    }

    @Transactional
    public void notifyLike(Trekker receiver, NotificationEntityType entityType, Long entityId) {
        String content = securityContextHolderHolder.getCurrentUser().getFullName() + " liked your " + entityType.name().toLowerCase().replace("_", " ");
        createNotification(receiver, securityContextHolderHolder.getCurrentUser(), resolveLikeType(entityType), entityType, entityId, content);
    }

    @Transactional
    public void notifyComment(Trekker receiver, NotificationEntityType entityType, Long entityId) {
        String content = securityContextHolderHolder.getCurrentUser().getFullName() + " commented on your story";
        createNotification(receiver, securityContextHolderHolder.getCurrentUser(), NotificationType.COMMENT, entityType, entityId, content);
    }

    @Transactional
    public void notifyReply(Trekker receiver, Long commentReplyId) {
        String content = securityContextHolderHolder.getCurrentUser().getFullName() + " replied to your comment";
        createNotification(receiver, securityContextHolderHolder.getCurrentUser(), NotificationType.REPLY_COMMENT,
                NotificationEntityType.COMMENT_REPLY, commentReplyId, content);
    }

    @Transactional(readOnly = true)
    public Page<InAppNotification> getNotifications(Long receiverId, Pageable pageable) {
        return notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId, pageable);
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(Long receiverId) {
        return notificationRepository.countByReceiverIdAndIsReadFalse(receiverId);
    }

    @Transactional
    public void markAsRead(Long notificationId, Long receiverId) {
        notificationRepository.markAsRead(notificationId, receiverId);
    }

    @Transactional
    public void markAllAsRead(Long receiverId) {
        notificationRepository.markAllAsRead(receiverId);
    }

    private NotificationType resolveLikeType(NotificationEntityType entityType) {
        return switch (entityType) {
            case STORY, STRATEGY -> NotificationType.LIKE_STORY;
            case STORY_COMMENT, STRATEGY_COMMENT -> NotificationType.LIKE_COMMENT;
            case COMMENT_REPLY -> NotificationType.LIKE_REPLY;
        };
    }
}