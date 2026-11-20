package com.trek.trekker.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.dto.response.ImageResponse;
import com.trek.core.dto.response.LikeResponse;
import com.trek.core.dto.response.SocialStatusResponse;
import com.trek.core.dto.response.UserResponse;
import com.trek.core.entity.Destination;
import com.trek.core.entity.Media;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekStory;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StoryDetailResponse {

    private Long id;
    private String title;
    private String slug;
    private String shortDescription;
    private String content;
    private LocalDate completedAt;
    private Integer durationDays;
    private Boolean isDraft;
    private List<String> highlights;
    private List<ImageResponse> images;
    private List<String> tags;
    private UserResponse writer;
    private StoryDestinationResponse destination;
    private LikeResponse likeCount;

    public static StoryDetailResponse from(TrekStory trekStory, LikeResponse likeResponse, List<MediaMapping> images, SocialStatusResponse socialStatus) {
        StoryDetailResponse detailResponse = new StoryDetailResponse();
        detailResponse.setContent(trekStory.getContent());
        detailResponse.setId(trekStory.getId());
        detailResponse.setTitle(trekStory.getTitle());
        detailResponse.setSlug(trekStory.getSlug());
        detailResponse.setShortDescription(trekStory.getShortDescription());
        detailResponse.setCompletedAt(trekStory.getCompletedAt());
        detailResponse.setDurationDays(trekStory.getDurationDays());
        detailResponse.setHighlights(trekStory.getHighlights());
        detailResponse.setTags(trekStory.getTags());
        detailResponse.setImages(ImageResponse.of(images));
        detailResponse.setLikeCount(likeResponse);
        detailResponse.setWriter(UserResponse.from(trekStory.getWriter(), socialStatus));
        if (trekStory.getDestination() != null) {
            detailResponse.setDestination(StoryDestinationResponse.from(trekStory.getDestination()));
        }
        return detailResponse;
    }

    @Data
    public static class StoryDestinationResponse {
        private Long id;
        private String destinationName;
        private String slug;

        public static StoryDestinationResponse from(Destination destination) {
            StoryDestinationResponse response = new StoryDestinationResponse();
            response.setId(destination.getId());
            response.setDestinationName(destination.getName());
            response.setSlug(destination.getSlug());
            return response;
        }
    }


}
