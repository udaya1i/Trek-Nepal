package com.trek.trekker.dto.response;

import com.trek.core.dto.response.TrekkerFollowProjection;
import com.trek.core.entity.Trekker;
import lombok.Data;

import java.util.List;

@Data
public class FollowTrekkerResponse {

    private Long id;
    private String name;
    private String avatar;
    private boolean isFriend;
    private String fullName;

    public static FollowTrekkerResponse from(Trekker trekker) {
        FollowTrekkerResponse followTrekkerResponse = new FollowTrekkerResponse();
        followTrekkerResponse.setId(trekker.getId());

        followTrekkerResponse.setName(trekker.getFullName());
        followTrekkerResponse.setAvatar(trekker.getAvatar());
        followTrekkerResponse.setFullName(trekker.getFullName());
        return followTrekkerResponse;
    }

    public static FollowTrekkerResponse from(TrekkerFollowProjection trekker) {
        FollowTrekkerResponse followTrekkerResponse = new FollowTrekkerResponse();
        followTrekkerResponse.setId(trekker.getId());
        followTrekkerResponse.setFriend(trekker.getIsFriend());
        followTrekkerResponse.setName(trekker.getFullName());
        followTrekkerResponse.setAvatar(trekker.getProfileImage());
        followTrekkerResponse.setFullName(trekker.getFullName());
        return followTrekkerResponse;
    }

    public static List<FollowTrekkerResponse> from(List<TrekkerFollowProjection> trekker) {
        return trekker.stream().map(FollowTrekkerResponse::from).toList();
    }
}
