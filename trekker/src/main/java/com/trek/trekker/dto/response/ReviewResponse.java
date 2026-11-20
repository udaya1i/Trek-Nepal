package com.trek.trekker.dto.response;

import com.trek.core.dto.response.ImageResponse;
import com.trek.core.entity.DestinationReview;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.Trekker;
import lombok.Data;

import java.util.List;

@Data
public class ReviewResponse {

    private Long id;
    private Long rating;
    private String message;
    private List<ImageResponse> images;
    private Reviewer reviewer;

    public static ReviewResponse from(DestinationReview review, List<MediaMapping> images) {
        ReviewResponse response = new ReviewResponse();
        response.setRating(review.getRating());
        response.setId(review.getId());
        response.setImages(ImageResponse.of(images));
        response.setMessage(review.getMessage());
        response.setReviewer(Reviewer.from(review.getTrekker()));
        return response;
    }


    @Data
    public static class Reviewer {
        private String name;
        private String avatar;
        private Long id;

        public static Reviewer from(Trekker trekker) {
            Reviewer reviewer = new Reviewer();
            reviewer.setName(trekker.getFullName());
            reviewer.setAvatar(trekker.getAvatar());
            reviewer.setId(trekker.getId());
            return reviewer;
        }
    }
}
