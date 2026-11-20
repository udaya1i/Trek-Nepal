package com.trek.core.dto.response;
public interface TrekkerSocialStats {
    long getFollowerCount();
    long getFollowingCount();
    boolean getIsFollowedByMe();
    boolean getIsFollowingMe();
    boolean getIsFriend();
    long getFriendCount();
}