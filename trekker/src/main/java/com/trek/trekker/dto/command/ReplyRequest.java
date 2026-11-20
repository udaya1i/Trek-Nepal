package com.trek.trekker.dto.command;

import com.trek.core.dto.request.Request;
import lombok.Data;

/**
 * @author udaya
 */
@Data
public class ReplyRequest implements Request {

    private String reply;
    private Long commentId;

}
