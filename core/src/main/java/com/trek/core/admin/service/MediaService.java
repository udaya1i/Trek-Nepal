package com.trek.core.admin.service;

import com.trek.core.config.ImageUploaderConfig;
import com.trek.core.constants.MediaStatus;
import com.trek.core.dto.request.ImageUpdateRequest;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.CloudinaryResponse;
import com.trek.core.entity.Media;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.MediaRepository;
import com.trek.core.util.ImageUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final ImageUploaderConfig imageUploaderConfig;

    public Media upload(CloudinaryResponse upload) {
        Media media = new Media();
        media.setImagePublicUrl(upload.imageUrl());
        media.setPublicId(upload.publicId());
        media.setDescription(upload.description());
        media.setImageAltMessage(upload.altMessage());
        media.setStatus(MediaStatus.PENDING);
        return mediaRepository.save(media);
    }

    @Async
    public void upload(ImageUploadRequest request, Media media) {
        CloudinaryResponse upload = ImageUploadUtil.uploadImages(request, imageUploaderConfig);
        updateMediaWithUpload(media, upload);
    }

    @Async
    public void uploadAsyncBytes(byte[] imageBytes, String originalFilename, String altMessage, String description, Long displayOrder, Media media) {
        CloudinaryResponse upload = imageUploaderConfig.uploadBytes(imageBytes, originalFilename, altMessage, description, displayOrder);
        updateMediaWithUpload(media, upload);
    }

    private void updateMediaWithUpload(Media media, CloudinaryResponse upload) {
        if (upload != null) {
            media.setImagePublicUrl(upload.imageUrl());
            media.setPublicId(upload.publicId());
            media.setDescription(upload.description());
            media.setImageAltMessage(upload.altMessage());
            mediaRepository.save(media);
        }
    }

    public Media findByUrl(String url) {
        return mediaRepository.findByImagePublicUrl(url)
                .orElseThrow(() -> new RequestValidationException("Image not found"));
    }

    public Media findByImageIdentifier(String key) {
        return mediaRepository.findByImagePublicUrl(key)
                .orElseThrow(() -> new RequestValidationException("Image not found"));
    }

    public List<Media> findAllByImageIdentifier(List<String> key) {
        return mediaRepository.findAllByImageIdentifierIn(key);
    }

    public List<Media> finaAllByUrl(List<String> images) {
        List<String> links = new ArrayList<>();
        List<String> codes = new ArrayList<>();

        images.forEach(image -> {
            if (image.startsWith("https://") || image.startsWith("http://")) {
                links.add(image);
            } else {
                codes.add(image);
            }
        });

        List<Media> medias = new ArrayList<>();

        if (!links.isEmpty()) {
            medias.addAll(mediaRepository.findAllByImagePublicUrlIn(links));
        }

        if (!codes.isEmpty()) {
            medias.addAll(mediaRepository.findAllByImageIdentifierIn(codes));
        }

        return medias;
    }

    public void delete(String imageUrl) {
        Media media = mediaRepository.findByImageIdentifier(imageUrl)
                .or(() -> mediaRepository.findByImagePublicUrl(imageUrl))
                .orElseThrow(() -> new RequestValidationException("Image not found"));
        ImageUploadUtil.delete(media.getPublicId(), imageUploaderConfig);
        media.setStatus(MediaStatus.DELETED);
        mediaRepository.save(media);
    }

    public void edit(ImageUpdateRequest request) {
        Media media = mediaRepository.findByImageIdentifier(request.getImageUrl())
                .or(() -> mediaRepository.findByImagePublicUrl(request.getImageUrl()))
                .orElseThrow(() -> new RequestValidationException("Image not found"));
        media.setDescription(request.getDescription());
        media.setSequence(request.getDisplayOrder());
        media.setImageAltMessage(request.getAltMessage());
        mediaRepository.save(media);
    }

    public void markAsActive(List<Media> images) {
        images.forEach(media -> media.setStatus(MediaStatus.ACTIVE));
        mediaRepository.saveAll(images);
    }

    public void markAsActive(Media images) {
        images.setStatus(MediaStatus.ACTIVE);
        mediaRepository.save(images);
    }
}