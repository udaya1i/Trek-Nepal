package com.trek.core.config;

import com.cloudinary.Cloudinary;
import com.trek.core.dto.response.CloudinaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author udaya
 */
@Slf4j
@Component
public class ImageUploaderConfig {

    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder:treks}")
    private String uploadFolder;

    public ImageUploaderConfig(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryResponse upload(MultipartFile images, String altMessage, String description, Long displayOrder) {
        if (images == null || images.isEmpty()) return null;
        try {
            return uploadBytes(images.getBytes(), images.getOriginalFilename(), altMessage, description, displayOrder);
        } catch (IOException e) {
            log.error("Failed to read image bytes: {}", images.getOriginalFilename(), e);
            throw new RuntimeException("Image upload failed: " + images.getOriginalFilename(), e);
        }
    }

    public CloudinaryResponse uploadBytes(byte[] imageBytes, String originalFilename, String altMessage, String description, Long displayOrder) {
        if (imageBytes == null || imageBytes.length == 0) return null;
        try {
            Map<String, Object> options = Map.of(
                    "folder", uploadFolder,
                    "resource_type", "image"
            );

            Map<?, ?> result = cloudinary.uploader().upload(imageBytes, options);
            String publicId = result.get("public_id").toString();
            String rawSecureUrl = result.get("secure_url").toString();

            String cleanSecureUrl = rawSecureUrl.replaceAll("cloudinary:/[^/]+/", "");

            return new CloudinaryResponse(publicId, cleanSecureUrl, description, altMessage, displayOrder);
        } catch (IOException e) {
            log.error("Failed to upload image to Cloudinary: {}", originalFilename, e);
            throw new RuntimeException("Image upload failed: " + originalFilename, e);
        }
    }

    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, Map.of("invalidate", true));
        } catch (IOException e) {
            log.error("Failed to delete image: {}", publicId, e);
            throw new RuntimeException("Failed to delete image: " + publicId, e);
        }
    }
}