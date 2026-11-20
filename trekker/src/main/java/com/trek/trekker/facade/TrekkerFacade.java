package com.trek.trekker.facade;

import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.constants.CommentEntityType;
import com.trek.core.constants.LikeEntityType;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.constants.TrekStoryStatus;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.SocialStatusResponse;
import com.trek.core.dto.response.TrekkerSocialStats;
import com.trek.core.entity.BaseEntity;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.Trekker;
import com.trek.core.constants.TrekkerStatus;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.exception.UnauthorizedException;
import com.trek.core.repository.CommentRepository;
import com.trek.core.repository.LikeRepository;
import com.trek.core.repository.TrekStoryRepository;
import com.trek.core.util.EntityCount;
import com.trek.trekker.dto.command.TrekkerCreateCommand;
import com.trek.trekker.dto.command.TrekkerEditCommand;
import com.trek.trekker.dto.request.TrekkerCreateRequest;
import com.trek.trekker.dto.request.TrekkerDeleteRequest;
import com.trek.trekker.dto.request.TrekkerEditRequest;
import com.trek.trekker.dto.request.requestValidator.TrekkerCreateRequestValidator;
import com.trek.trekker.dto.request.requestValidator.TrekkerEditRequestValidator;
import com.trek.trekker.dto.response.StoriesResponse;
import com.trek.trekker.dto.response.TrekkerDetailResponse;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
//import com.trek.trekker.service.TrekkerFollowerService;
import com.trek.trekker.service.TrekkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrekkerFacade {

    private final TrekkerService trekkerService;
    private final TrekStoryRepository storyRepository;
    private final TrekkerCreateRequestValidator requestValidator;
    private final TrekkerEditRequestValidator editRequestValidator;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
//    private final TrekkerFollowerService followerService;
    private final MediaMappingService mediaMappingService;
    private final TrekkerSecurityContextHolderHolder securityContextHolderHolder;
    private final PasswordEncoder passwordEncoder;

    public ServerResponse<List<TrekkerDetailResponse>> getAll() {

        return ServerResponse.<List<TrekkerDetailResponse>>builder()
                .message("Trekkers fetched successfully")
                .status(HttpStatus.OK)
                .data(TrekkerDetailResponse.of(trekkerService.getAll()))
                .build();
    }

    @Transactional
    public ServerResponse<TrekkerDetailResponse> getById(Long id) {
        Trekker trekker = trekkerService.getById(id);

        List<TrekStory> allUserStory = storyRepository.findAllByWriterAndStatus(trekker, TrekStoryStatus.ACTIVE);

        List<Long> storyIds = allUserStory.stream()
                .map(TrekStory::getId)
                .toList();

        Map<Long, List<MediaMapping>> storyImageMap = mediaMappingService.getAllImage(storyIds, MediaOwnerType.TREKKER_STORY)
                .stream().collect(Collectors.groupingBy(MediaMapping::getOwnerId));

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
                    return StoriesResponse.from(story, likeCount, commentCount, storyImageMap.getOrDefault(story.getId(), List.of()));
                })
                .toList();
//        TrekkerSocialStats socialStats = followerService.getSocialStats(trekker);

        return ServerResponse.<TrekkerDetailResponse>builder()
                .status(HttpStatus.OK)
                .data(TrekkerDetailResponse.of(trekker, storiesResponse, null))
                .message("Trekker detail fetched")
                .build();
    }

    public ServerResponse<TrekkerDetailResponse> create(TrekkerCreateRequest request) {
        TrekkerCreateCommand command = requestValidator.validate(request);
        Trekker trekker = trekkerService.create(command);
        return ServerResponse.<TrekkerDetailResponse>builder()
                .message("Trekker account created successfully")
                .data(TrekkerDetailResponse.of(trekker))
                .status(HttpStatus.CREATED)
                .build();
    }

    public ServerResponse<Void> uploadProfile(ImageUploadRequest request) {
        trekkerService.uploadProfile(request);
        return ServerResponse.<Void>builder()
                .message("Profile updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<Void> uploadCover(ImageUploadRequest request) {
        trekkerService.uploadCover(request);
        return ServerResponse.<Void>builder()
                .message("Cover image updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<TrekkerDetailResponse> edit(Long id, TrekkerEditRequest request) {
        Trekker currentUser = securityContextHolderHolder.getCurrentUser();
        if (currentUser == null || !currentUser.getId().equals(id)) {
            throw new UnauthorizedException("You can only edit your own profile");
        }

        TrekkerEditCommand command = editRequestValidator.validate(request);
        Trekker trekker = trekkerService.edit(command);
        return ServerResponse.<TrekkerDetailResponse>builder()
                .data(TrekkerDetailResponse.of(trekker))
                .message("Trekker profile updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<Void> delete(Long id, TrekkerDeleteRequest request) {
        Trekker currentUser = securityContextHolderHolder.getCurrentUser();
        if (currentUser == null || !currentUser.getId().equals(id)) {
            throw new UnauthorizedException("You can't perform this action");
        }
        if (!passwordEncoder.matches(request.getPassword(), currentUser.getPassword())) {
            throw new RequestValidationException("Incorrect password");
        }

        currentUser.setStatus(TrekkerStatus.DELETED);
        trekkerService.delete(currentUser);
        return ServerResponse.<Void>builder()
                .message("Trekker account deleted successfully")
                .status(HttpStatus.OK)
                .build();
    }
}
