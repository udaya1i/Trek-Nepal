package com.trek.trekker.dto.request;

import com.trek.core.constants.CommentEntityType;
import lombok.Data;

@Data
public class TrekkerStoryCommentRequest {

    private String content;
    private CommentEntityType entityType;
}
