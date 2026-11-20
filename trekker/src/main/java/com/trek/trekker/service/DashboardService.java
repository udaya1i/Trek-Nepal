package com.trek.trekker.service;

import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.constants.*;
import com.trek.core.dto.response.DestinationCategoryCountProjection;
import com.trek.core.entity.BaseEntity;
import com.trek.core.entity.Destination;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekStory;
import com.trek.core.repository.*;
import com.trek.core.util.EntityCount;
import com.trek.trekker.dto.response.ApplicationStatusResponse;
import com.trek.trekker.dto.response.PublicDestinationResponse;
import com.trek.trekker.dto.response.StoriesResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DashboardService {

    private final DestinationQueryService destinationQueryService;
    private final TrekStoryRepository storyRepository;
    private final TrekStoryRepository trekStoryRepository;
    private final DestinationRepository destinationRepository;
    private final TrekkerRepository trekkerRepository;
    private final MediaMappingService mediaMappingService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final DestinationReviewService destinationReviewService;

    @Transactional(readOnly = true)
    public List<PublicDestinationResponse> popularDestinations() {
        List<Destination> destinations = destinationQueryService.featuredTreks();

        List<Long> ids = destinations.stream()
                .map(BaseEntity::getId)
                .toList();

        Map<Long, List<MediaMapping>> mediaMap =
                mediaMappingService.getAllImage(ids, MediaOwnerType.DESTINATION)
                        .stream()
                        .collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        return destinations.stream().map(destination -> {
            return PublicDestinationResponse.of(destination, mediaMap.getOrDefault(destination.getId(), List.of()), destinationReviewService.getStatus(destination.getId()));
        }).toList();
    }

    public List<StoriesResponse> popularStories() {

        List<TrekStory> trekStories = storyRepository.findTop5ByOrderByViewsDesc();
        List<Long> storyIds = trekStories
                .stream()
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
            likeCountMap = Collections.emptyMap();
            commentCountMap = Collections.emptyMap();
        }
        return trekStories.stream()
                .map(story -> {
                    long commentCount = commentCountMap.getOrDefault(story.getId(), 0L);
                    long likeCount = likeCountMap.getOrDefault(story.getId(), 0L);
                    return StoriesResponse.from(story, likeCount, commentCount, collect.getOrDefault(story.getId(), List.of()));
                })
                .toList();
    }


    public ApplicationStatusResponse getPlatformStats() {
        return ApplicationStatusResponse.from(trekkerRepository.countByStatus(TrekkerStatus.ACTIVE),
                destinationRepository.countByDestinationStatus(DestinationStatus.ACTIVE),
                trekStoryRepository.countByStatus(TrekStoryStatus.ACTIVE));
    }

    public List<DestinationCategoryCountProjection> getTrailCategory() {
        return destinationRepository.countDestinationByTrailCategory();
    }

}
