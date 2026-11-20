package com.trek.core.repository;

import com.trek.core.entity.InAppNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InAppNotificationRepository extends JpaRepository<InAppNotification, Long> {

    Page<InAppNotification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId, Pageable pageable);

    long countByReceiverIdAndIsReadFalse(Long receiverId);

    @Modifying
    @Query("update InAppNotification n set n.isRead = true, n.readAt = CURRENT_TIMESTAMP " +
            "where n.id = :id and n.receiver.id = :receiverId")
    void markAsRead(@Param("id") Long id, @Param("receiverId") Long receiverId);

    @Modifying
    @Query("update InAppNotification n set n.isRead = true, n.readAt = CURRENT_TIMESTAMP " +
            "where n.receiver.id = :receiverId and n.isRead = false")
    void markAllAsRead(@Param("receiverId") Long receiverId);
}