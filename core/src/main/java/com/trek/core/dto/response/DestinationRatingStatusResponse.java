package com.trek.core.dto.response;

public interface DestinationRatingStatusResponse {
    Long getTotalReviews();
    Double getAverageRating();
    Long getDestinationId();
}