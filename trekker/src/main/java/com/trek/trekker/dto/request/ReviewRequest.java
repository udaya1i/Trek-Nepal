package com.trek.trekker.dto.request;

import com.trek.core.annotation.Required;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class ReviewRequest implements Request {

    @Required(field = "rating", min = 1, max = 5)
    private Long rating;
    private String message;
    private Long destination;
    private List<String> images;
}
