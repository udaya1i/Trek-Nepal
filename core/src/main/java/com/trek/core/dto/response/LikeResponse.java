package com.trek.core.dto.response;

public record LikeResponse(
        long totalLikes,
        boolean likedByMe
) {}