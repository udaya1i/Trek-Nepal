package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.entity.Media;
import com.trek.core.entity.MediaMapping;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ImageResponse {

    private String imageUrl;
    private String description;
    private Long order;
    private String altMessage;

    public static ImageResponse of(MediaMapping mediaMap) {
        ImageResponse response = new ImageResponse();
        Media media = mediaMap.getMedia();
        response.setOrder(mediaMap.getSequenceOrder());
        response.setDescription(media.getDescription());
        response.setImageUrl(media.getImagePublicUrl());
        response.setAltMessage(media.getImageAltMessage());
        return response;
    }

    public static List<ImageResponse> of(List<MediaMapping> media) {
        return media.stream().map(ImageResponse::of).toList();
    }
}
