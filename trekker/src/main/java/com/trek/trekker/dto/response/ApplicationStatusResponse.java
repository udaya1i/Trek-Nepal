package com.trek.trekker.dto.response;


public record ApplicationStatusResponse(Long totalTrekkers, Long totalDestinations, Long totalStories) {
    public static ApplicationStatusResponse from(Long totalTrekkers, Long totalDestinations, Long totalStories) {
        return new ApplicationStatusResponse(totalTrekkers, totalDestinations, totalStories);
    }
}
