package com.trek.admin.facade;

import com.trek.core.dto.response.ImageUploadResponse;
import com.trek.core.service.ImageService;
import com.trek.core.dto.request.ImageDeleteRequest;
import com.trek.core.dto.request.ImageUpdateRequest;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.Media;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminImageUploadFacade {

    private final ImageService imageService;

    public ServerResponse<ImageUploadResponse> upload(ImageUploadRequest request) {
        Media media = imageService.upload(request);
        return ServerResponse.<ImageUploadResponse>builder()
                .status(HttpStatus.OK)
                .message("Image uploaded successfully")
                .data(ImageUploadResponse.from(media))
                .build();
    }

    public ServerResponse<Void> delete(ImageDeleteRequest request) {
        imageService.delete(request.getUrl());
        return ServerResponse.<Void>builder()
                .message("Image deleted")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<Void> edit(ImageUpdateRequest request) {
        imageService.edit(request);
        return ServerResponse.<Void>builder()
                .message("Image edited")
                .status(HttpStatus.OK)
                .build();
    }
}
