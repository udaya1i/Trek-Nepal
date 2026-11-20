package com.trek.trekker.dto.command;

import com.trek.core.annotation.EnumValidator;
import com.trek.core.constants.CommentEntityType;
import com.trek.core.dto.request.Request;
import lombok.Data;

/**
 * @author udaya
 */
@Data
public class CommentRequest implements Request {

    private Long entityId;

    @EnumValidator(field = "Entity type", enumClass = CommentEntityType.class, required = true)
    private String entityType;

    private String comment;

}
