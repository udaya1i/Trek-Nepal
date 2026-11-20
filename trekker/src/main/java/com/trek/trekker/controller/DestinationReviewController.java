package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.request.PaginationRequest;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import com.trek.trekker.dto.request.ReviewRequest;
import com.trek.trekker.dto.response.ReviewResponse;
import com.trek.trekker.dto.response.ReviewStatusResponse;
import com.trek.trekker.facade.DestinationReviewFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.DESTINATIONS)
public class DestinationReviewController {

    private final DestinationReviewFacade facade;

    @PreAuthorize(Authorities.REVIEW_DESTINATION)
    @PostMapping(PublicPathConstant.REVIEWS)
    public ResponseEntity<ServerResponse<ReviewResponse>> review(@RequestBody ReviewRequest request) {
        ServerResponse<ReviewResponse> serverResponse = facade.review(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.REVIEW_DESTINATION)
    @PostMapping(PublicPathConstant.ID + PublicPathConstant.REVIEWS)
    public ResponseEntity<PaginatedResponse<ReviewResponse>> getAll(@PathVariable("id") Long destinationId, @RequestBody PaginationRequest request) {
        PaginatedResponse<ReviewResponse> serverResponse = facade.getAll(request, destinationId);
        return ResponseEntity.status(serverResponse.getStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.REVIEW_DESTINATION)
    @GetMapping(PublicPathConstant.ID + PublicPathConstant.REVIEWS + PublicPathConstant.STATUS)
    public ResponseEntity<ServerResponse<ReviewStatusResponse>> reviewCount(@PathVariable("id") Long destinationId) {
        ServerResponse<ReviewStatusResponse> serverResponse = facade.getRating(destinationId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
