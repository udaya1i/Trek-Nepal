package com.trek.core.dto.response;

import com.trek.core.entity.Media;

public record ImageUploadResponse(String publicUrl) {

    public static ImageUploadResponse from(Media media) {
        return new ImageUploadResponse(media.getImageIdentifier());
    }
}
