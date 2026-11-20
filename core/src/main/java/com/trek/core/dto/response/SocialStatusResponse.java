package com.trek.core.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialStatusResponse {

    private long followerCount;
    private long followingCount;
    private Boolean isFollowedByMe;
    private Boolean isFollowingMe;
    private Boolean isFriend;
    private long friendCount;

    public static SocialStatusResponse from(TrekkerSocialStats socialStats) {
        return new SocialStatusResponse(
                socialStats.getFollowerCount(),
                socialStats.getFollowingCount(),
                socialStats.getIsFollowedByMe(),
                socialStats.getIsFollowingMe(),
                socialStats.getIsFriend(),
                socialStats.getFriendCount()
        );
    }
}