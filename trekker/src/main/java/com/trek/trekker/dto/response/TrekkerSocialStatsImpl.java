package com.trek.trekker.dto.response;

import com.trek.core.dto.response.TrekkerSocialStats;

public record TrekkerSocialStatsImpl(
        long followerCount,
        long followingCount,
        boolean isFollowedByMe,
        boolean isFollowingMe,
        long friendCount
) implements TrekkerSocialStats {

    @Override public long getFollowerCount()    { return followerCount; }
    @Override public long getFollowingCount()   { return followingCount; }
    @Override public boolean getIsFollowedByMe(){ return isFollowedByMe; }
    @Override public boolean getIsFollowingMe() { return isFollowingMe; }
    @Override public boolean getIsFriend()      { return isFollowedByMe && isFollowingMe; }
    @Override public long getFriendCount()      { return friendCount; }
}