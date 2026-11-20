package com.trek.core.service;

import com.trek.core.admin.service.MediaService;
import com.trek.core.constants.MediaStatus;
import com.trek.core.dto.request.ImageUpdateRequest;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.entity.Media;
import com.trek.core.repository.MediaRepository;
import com.trek.core.util.ImageUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageService {

    private final MediaService mediaService;
    private final MediaRepository mediaRepository;

    public Media upload(ImageUploadRequest request) {
        String key = ImageUploadUtil.generateImageIdentifier();
        Media media = new Media();
        media.setStatus(MediaStatus.PENDING);
        media.setImageIdentifier(key);
        mediaRepository.save(media);

        try {
            byte[] fileBytes = request.getImages() != null ? request.getImages().getBytes() : null;
            String originalFilename = request.getImages() != null ? request.getImages().getOriginalFilename() : null;
            mediaService.uploadAsyncBytes(fileBytes, originalFilename, request.getAltMessage(), request.getDescription(), request.getDisplayOrder(), media);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read uploaded image bytes", e);
        }

        return media;
    }

    public void delete(String url) {
        mediaService.delete(url);
    }

    public void edit(ImageUpdateRequest request) {
        mediaService.edit(request);
    }
}