package com.trek.core.dto.response;
public interface TrekkerFollowProjection {
    Long getId();
    String getUsername();
    String getFullName();
    String getProfileImage();
    boolean getIsFriend();  
}