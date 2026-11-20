package com.trek.trekker.dto.request;

import com.trek.core.annotation.EnumValidator;
import com.trek.core.constants.CommentEntityType;
import lombok.Data;

@Data
public class CommentLikeRequest {

    @EnumValidator(field = "Entity Type", enumClass = CommentEntityType.class, required = true)
    private CommentEntityType entityType;

}
