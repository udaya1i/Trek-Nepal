package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public record CloudinaryResponse(String publicId, String imageUrl, String description, String altMessage, Long displayOrder) {
}
