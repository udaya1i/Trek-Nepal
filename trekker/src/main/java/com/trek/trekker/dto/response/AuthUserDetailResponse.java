package com.trek.trekker.dto.response;

import com.trek.core.entity.Trekker;
import lombok.Data;

@Data
public class AuthUserDetailResponse {

    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String avatar;
    private String coverImage;
    private String bio;
    private String location;
    private String isVerified;

    public static AuthUserDetailResponse from(Trekker trekker) {
        AuthUserDetailResponse response = new AuthUserDetailResponse();
        response.setFullName(trekker.getFullName());
        response.setUsername(trekker.getUsername());
        response.setId(trekker.getId());
        response.setEmail(trekker.getEmail());
        response.setAvatar(trekker.getAvatar());
        response.setCoverImage(trekker.getCoverImage());
        response.setBio(trekker.getBio());
        response.setLocation(trekker.getLocation());
        response.setIsVerified(trekker.getIsVerified());
        return response;
    }
}
