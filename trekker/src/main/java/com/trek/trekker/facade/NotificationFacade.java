package com.trek.trekker.facade;

import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.InAppNotification;
import com.trek.core.util.PaginationUtil;
import com.trek.trekker.dto.response.NotificationResponse;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import com.trek.trekker.service.InAppNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationFacade {

    private final InAppNotificationService inAppNotificationService;
    private final TrekkerSecurityContextHolderHolder trekkerSecurityContextHolderHolder;

    public PaginatedResponse<NotificationResponse> getAll(FilterRequest filterRequest) {
        Long receiverId = trekkerSecurityContextHolderHolder.getCurrentUser().getId();

        Page<InAppNotification> notifications = inAppNotificationService.getNotifications(receiverId, PaginationUtil.of(filterRequest.getPaginationRequest()));

        return PaginatedResponse
                .data(notifications.map(NotificationResponse::from))
                .status(HttpStatus.OK)
                .message("Notifications fetched successfully")
                .build();
    }

    public ServerResponse<Map<String, Long>> getUnreadCount() {
        Long receiverId = trekkerSecurityContextHolderHolder.getCurrentUser().getId();
        long count = inAppNotificationService.getUnreadCount(receiverId);
        return ServerResponse.<Map<String, Long>>builder()
                .status(HttpStatus.OK)
                .data(Map.of("count", count))
                .message("Unread count fetched successfully")
                .build();
    }

    public ServerResponse<Void> markAsRead(Long notificationId) {
        Long receiverId = trekkerSecurityContextHolderHolder.getCurrentUser().getId();
        inAppNotificationService.markAsRead(notificationId, receiverId);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Notification marked as read")
                .build();
    }

    public ServerResponse<Void> markAllAsRead() {
        Long receiverId = trekkerSecurityContextHolderHolder.getCurrentUser().getId();
        inAppNotificationService.markAllAsRead(receiverId);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("All notifications marked as read")
                .build();
    }
}