package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import com.trek.trekker.dto.response.NotificationResponse;
import com.trek.trekker.facade.NotificationFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.NOTIFICATION)
@AllArgsConstructor
public class NotificationController {

    private final NotificationFacade notificationFacade;

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @PostMapping
    public ResponseEntity<PaginatedResponse<NotificationResponse>> getAll(@RequestBody FilterRequest filterRequest) {
        PaginatedResponse<NotificationResponse> response = notificationFacade.getAll(filterRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @GetMapping(PublicPathConstant.COUNT)
    public ResponseEntity<ServerResponse<Map<String, Long>>> getUnreadCount() {
        ServerResponse<Map<String, Long>> response = notificationFacade.getUnreadCount();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @PostMapping(PublicPathConstant.ID + PublicPathConstant.READ)
    public ResponseEntity<ServerResponse<Void>> markAsRead(@PathVariable("id") Long notificationId) {
        ServerResponse<Void> response = notificationFacade.markAsRead(notificationId);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @PostMapping(PublicPathConstant.READ_ALL)
    public ResponseEntity<ServerResponse<Void>> markAllAsRead() {
        ServerResponse<Void> response = notificationFacade.markAllAsRead();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}