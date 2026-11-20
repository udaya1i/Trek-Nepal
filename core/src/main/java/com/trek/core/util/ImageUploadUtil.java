package com.trek.core.util;

import com.trek.core.config.ImageUploaderConfig;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.CloudinaryResponse;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;


@UtilityClass
public class ImageUploadUtil {

    public static CloudinaryResponse uploadImages(ImageUploadRequest imageUploadRequest, ImageUploaderConfig imageUploaderConfig) {
        return imageUploaderConfig.upload(imageUploadRequest.getImages(),
                imageUploadRequest.getAltMessage(),
                imageUploadRequest.getDescription(),
                imageUploadRequest.getDisplayOrder());
    }

    public static void delete(String publicUrl, ImageUploaderConfig imageUploaderConfig) {
        imageUploaderConfig.deleteImage(publicUrl);
    }

    public static String generateImageIdentifier() {
        return UUID.randomUUID() + LocalDateTime.now().toString();
    }

}
