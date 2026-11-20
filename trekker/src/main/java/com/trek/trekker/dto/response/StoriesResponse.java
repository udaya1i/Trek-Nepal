package com.trek.trekker.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.dto.response.ImageResponse;
import com.trek.core.dto.response.UserResponse;
import com.trek.core.entity.Media;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekStory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoriesResponse {
    private Long id;
    private String title;
    private String slug;
    private String shortDescription;
    private Long likeCount;
    private Long commentCount;
    private UserResponse writer;
    private LocalDateTime publishedAt;
    private StoryDetailResponse.StoryDestinationResponse destination;
    private List<ImageResponse> images;

    public static StoriesResponse from(TrekStory story, Long likeCount, Long commentCount, List<MediaMapping> images) {
        StoriesResponse response = new StoriesResponse();
        response.setId(story.getId());
        response.setTitle(story.getTitle());
        response.setSlug(story.getSlug());
        response.setShortDescription(story.getShortDescription());
        response.setLikeCount(likeCount);
        response.setImages(ImageResponse.of(images));
        response.setCommentCount(commentCount);
        response.setWriter(UserResponse.from(story.getWriter()));
        response.setPublishedAt(story.getCreatedAt());
        if (story.getDestination() != null)
            response.setDestination(StoryDetailResponse.StoryDestinationResponse.from(story.getDestination()));
        return response;
    }


}
