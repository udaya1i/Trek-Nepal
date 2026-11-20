package com.trek.trekker.dto.response;

import com.trek.core.dto.response.SocialStatusResponse;
import com.trek.core.entity.Trekker;
import lombok.Data;

import java.util.List;


@Data
public class TrekkerDetailResponse {
    private String fullName;
    private String username;
    private String email;
    private String avatar;
    private String coverImage;
    private String bio;
    private String location;
    private Long id;
    private SocialStatusResponse socialStatus;
    private List<StoriesResponse> stories;

    public static List<TrekkerDetailResponse> of(List<Trekker> trekkers) {
        return trekkers.stream().map(TrekkerDetailResponse::of).toList();
    }

    public static TrekkerDetailResponse of(Trekker trekker) {
        TrekkerDetailResponse response = new TrekkerDetailResponse();
        response.setFullName(trekker.getFullName());
        response.setEmail(trekker.getEmail());
        response.setAvatar(trekker.getAvatar());
        response.setCoverImage(trekker.getCoverImage());
        response.setId(trekker.getId());
        response.setBio(trekker.getBio());
        response.setLocation(trekker.getLocation());
        response.setUsername(trekker.getUsername());
        return response;
    }

    public static TrekkerDetailResponse of(Trekker trekker, List<StoriesResponse> stories, SocialStatusResponse socialStatusResponse) {
        TrekkerDetailResponse response = new TrekkerDetailResponse();
        response.setFullName(trekker.getFullName());
        response.setEmail(trekker.getEmail());
        response.setAvatar(trekker.getAvatar());
        response.setCoverImage(trekker.getCoverImage());
        response.setBio(trekker.getBio());
        response.setId(trekker.getId());
        response.setLocation(trekker.getLocation());
        response.setUsername(trekker.getUsername());
        response.setSocialStatus(socialStatusResponse);
        response.setStories(stories);
        return response;
    }
}
