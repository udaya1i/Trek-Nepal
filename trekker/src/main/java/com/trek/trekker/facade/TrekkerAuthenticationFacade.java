package com.trek.trekker.facade;

import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.constants.CommentEntityType;
import com.trek.core.constants.LikeEntityType;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.constants.TrekStoryStatus;
import com.trek.core.dto.request.ForgotPasswordRequest;
import com.trek.core.dto.request.LoginRequest;
import com.trek.core.dto.request.ResetPasswordRequest;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.Trekker;
import com.trek.core.repository.CommentRepository;
import com.trek.core.repository.LikeRepository;
import com.trek.core.repository.TrekStoryRepository;
import com.trek.core.util.EntityCount;
import com.trek.trekker.dto.response.StoriesResponse;
import com.trek.trekker.dto.response.TrekkerAuthenticationResponse;
import com.trek.trekker.dto.response.TrekkerDetailResponse;
import com.trek.trekker.service.TrekkerAuthenticationService;
import com.trek.trekker.service.TrekkerPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrekkerAuthenticationFacade {

    private final TrekkerAuthenticationService authenticationService;
    private final TrekStoryRepository storyRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final MediaMappingService mediaMappingService;
    private final TrekkerPasswordService passwordService;


    public ServerResponse<TrekkerAuthenticationResponse> authenticate(LoginRequest request) {
        TrekkerAuthenticationResponse loginResponse = authenticationService.login(request);
        return loginResponse.isAuthenticated() ?
                ServerResponse.<TrekkerAuthenticationResponse>builder()
                        .message("Login successful")
                        .header("Authorization", loginResponse.getAccessToken())
                        .header("X-Refresh-Token", loginResponse.getRefreshToken())
                        .data(loginResponse)
                        .status(HttpStatus.OK)
                        .build() :
                ServerResponse.<TrekkerAuthenticationResponse>builder()
                        .message("Invalid credentials")
                        .data(loginResponse)
                        .status(HttpStatus.UNAUTHORIZED)
                        .build();
    }

    public ServerResponse<Void> checkUsername(String username) {
        return authenticationService.checkUsername(username) ?
                ServerResponse.<Void>builder()
                        .message("Username already taken")
                        .status(HttpStatus.BAD_REQUEST)
                        .build() :
                ServerResponse.<Void>builder()
                        .message("Username is available")
                        .status(HttpStatus.OK)
                        .build();
    }

    public ServerResponse<Void> checkEmail(String email) {
        return authenticationService.checkEmail(email) ?
                ServerResponse.<Void>builder()
                        .message("Email already used")
                        .status(HttpStatus.BAD_REQUEST)
                        .build() :
                ServerResponse.<Void>builder()
                        .message("Email is available")
                        .status(HttpStatus.OK)
                        .build();
    }

    public ServerResponse<TrekkerAuthenticationResponse> refreshToken(LoginRequest request) {
        TrekkerAuthenticationResponse loginResponse = authenticationService.login(request);
        return loginResponse.isAuthenticated() ?
                ServerResponse.<TrekkerAuthenticationResponse>builder()
                        .message("Login successful")
                        .header("Authorization", loginResponse.getAccessToken())
                        .header("X-Refresh-Token", loginResponse.getRefreshToken())
                        .data(loginResponse)
                        .status(HttpStatus.OK)
                        .build() :
                ServerResponse.<TrekkerAuthenticationResponse>builder()
                        .message("Invalid credentials")
                        .data(loginResponse)
                        .status(HttpStatus.UNAUTHORIZED)
                        .build();

    }

    //TODO: Centralize user detail response
    public ServerResponse<TrekkerDetailResponse> authUserDetail() {
        Trekker trekker = authenticationService.authUserDetail();

        List<TrekStory> allUserStory = storyRepository.findAllByWriterAndStatus(trekker, TrekStoryStatus.ACTIVE);

        List<Long> storyIds = allUserStory.stream()
                .map(TrekStory::getId)
                .toList();


        Map<Long, List<MediaMapping>> collect = mediaMappingService.getAllImage(storyIds, MediaOwnerType.TREKKER_STORY).stream()
                .collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        Map<Long, Long> commentCountMap;
        Map<Long, Long> likeCountMap;

        if (!storyIds.isEmpty()) {
            commentCountMap = commentRepository
                    .countByEntityTypeAndEntityIdIn(CommentEntityType.STORY, storyIds)
                    .stream()
                    .collect(Collectors.toMap(
                            EntityCount::getEntityId,
                            EntityCount::getCount
                    ));

            likeCountMap = likeRepository
                    .countLikesByEntityTypeAndEntityIdIn(LikeEntityType.STORY.name(), storyIds)
                    .stream()
                    .collect(Collectors.toMap(
                            EntityCount::getEntityId,
                            EntityCount::getCount
                    ));
        } else {
            commentCountMap = Collections.emptyMap();
            likeCountMap = Collections.emptyMap();
        }

        List<StoriesResponse> storiesResponse = allUserStory.stream()
                .map(story -> {
                    long commentCount = commentCountMap.getOrDefault(story.getId(), 0L);
                    long likeCount = likeCountMap.getOrDefault(story.getId(), 0L);
                    return StoriesResponse.from(story, likeCount, commentCount, collect.getOrDefault(story.getId(), List.of()));
                })
                .toList();
//        TrekkerSocialStats socialStats = followerService.getSocialStats(trekker);

        return ServerResponse.<TrekkerDetailResponse>builder()
                .status(HttpStatus.OK)
                .data(TrekkerDetailResponse.of(trekker, storiesResponse, null))
                .message("Trekker detail fetched")
                .build();

    }


    public ServerResponse<Void> forgotPassword(ForgotPasswordRequest request) {
        passwordService.forgotPassword(request.getEmail());
        return ServerResponse.<Void>builder()
                .message("If an account exists with this email, an OTP has been sent")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<Void> resetPassword(ResetPasswordRequest request) {
        try {
            passwordService.resetPassword(request);
            return ServerResponse.<Void>builder()
                    .message("Password reset successful")
                    .status(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException e) {
            return ServerResponse.<Void>builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
