package com.trek.core.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadRequest {

    private MultipartFile images;
    private String altMessage;
    private String description;
    private Long displayOrder;
}
