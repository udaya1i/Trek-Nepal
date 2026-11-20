package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.entity.Admin;
import com.trek.core.entity.Trekker;
import lombok.Data;

/**
 * @author udaya
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserResponse {

    private String username;
    private String fullName;
    private String avatar;
    private Long id;
    private String bio;
//    private Long follower;
//    private long followerCount;
//    private long followingCount;
//    private Boolean isFollowedByMe;
//    private Boolean isFollowingMe;
//    private Boolean isFriend;


    public static UserResponse from(Admin admin) {
        UserResponse response = new UserResponse();
        response.setId(admin.getId());
        response.setAvatar(admin.getAvatar());
        response.setUsername(admin.getUsername());
        response.setFullName(admin.getName());
        return response;
    }

    public static UserResponse from(Trekker trekker, SocialStatusResponse socialStatusResponse) {
        UserResponse response = new UserResponse();
        response.setId(trekker.getId());
        response.setAvatar(trekker.getAvatar());
//        response.setFollowerCount(socialStatusResponse.getFollowerCount());
//        response.setFollowingCount(socialStatusResponse.getFollowingCount());
//        response.setIsFollowedByMe(socialStatusResponse.getIsFollowedByMe());
//        response.setIsFollowingMe(socialStatusResponse.getIsFollowingMe());
//        response.setIsFriend(socialStatusResponse.getIsFriend());
        response.setBio(trekker.getBio());
        response.setUsername(trekker.getUsername());
        response.setFullName(trekker.getFullName());
        return response;
    }

    public static UserResponse from(Trekker trekker) {
        UserResponse response = new UserResponse();
        response.setId(trekker.getId());
        response.setAvatar(trekker.getAvatar());
        response.setBio(trekker.getBio());
        response.setUsername(trekker.getUsername());
        response.setFullName(trekker.getFullName());
        return response;
    }
}
