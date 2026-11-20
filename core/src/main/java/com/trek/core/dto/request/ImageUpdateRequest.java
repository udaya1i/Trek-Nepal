package com.trek.core.dto.request;

import lombok.Data;

@Data
public class ImageUpdateRequest {

    private String imageUrl;
    private String altMessage;
    private String description;
    private Long displayOrder;

}
