package com.trek.trekker.dto.response;

import com.trek.core.dto.response.DestinationRatingStatusResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class ReviewStatusResponse {

    private Long totalReviews;
    private Double averageRating;
    private Long destinationId;

    public static ReviewStatusResponse from(DestinationRatingStatusResponse response) {
        return ReviewStatusResponse.builder()
                .totalReviews(response != null ? response.getTotalReviews() : 0)
                .averageRating(response != null ? response.getAverageRating() : 0)
                .destinationId(response != null ? response.getDestinationId() : 0)
                .build();
    }
}
