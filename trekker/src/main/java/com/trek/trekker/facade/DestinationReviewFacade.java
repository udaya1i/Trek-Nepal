package com.trek.trekker.facade;

import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.dto.request.PaginationRequest;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.BaseEntity;
import com.trek.core.entity.DestinationReview;
import com.trek.core.entity.MediaMapping;
import com.trek.trekker.dto.request.ReviewRequest;
import com.trek.trekker.dto.response.ReviewResponse;
import com.trek.trekker.dto.response.ReviewStatusResponse;
import com.trek.trekker.service.DestinationReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DestinationReviewFacade {

    private final DestinationReviewService destinationReviewService;
    private final MediaMappingService mediaMappingService;

    public ServerResponse<ReviewResponse> review(ReviewRequest request) {
        DestinationReview review = destinationReviewService.review(request);
        Map<Long, List<MediaMapping>> collect = mediaMappingService.getAllImage(review.getId(), MediaOwnerType.REVIEW)
                .stream().collect(Collectors.groupingBy(MediaMapping::getOwnerId));
        return ServerResponse.<ReviewResponse>builder()
                .data(ReviewResponse.from(review, collect.getOrDefault(review.getId(), List.of())))
                .message("Review added successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public PaginatedResponse<ReviewResponse> getAll(PaginationRequest request, Long destinationId) {
        Page<DestinationReview> destinationReviews = destinationReviewService.getAll(request, destinationId);

        List<Long> ids = destinationReviews.getContent().stream().map(BaseEntity::getId).toList();

        Map<Long, List<MediaMapping>> collect = mediaMappingService.getAllImage(ids, MediaOwnerType.REVIEW)
                .stream().collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        return PaginatedResponse.data(destinationReviews.map(r -> ReviewResponse.from(r, collect.getOrDefault(r.getId(), List.of()))))
                .message("Review(s) fetched successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<ReviewStatusResponse> getRating(Long destinationId) {
        return ServerResponse.<ReviewStatusResponse>builder()
                .message("Review rating fetched successfully")
                .data(ReviewStatusResponse.from(destinationReviewService.getStatus(destinationId)))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<ReviewStatusResponse> delete(Long ratingId) {
        destinationReviewService.delete(ratingId);
        return ServerResponse.<ReviewStatusResponse>builder()
                .message("Review deleted successfully")
                .status(HttpStatus.OK)
                .build();
    }
}
