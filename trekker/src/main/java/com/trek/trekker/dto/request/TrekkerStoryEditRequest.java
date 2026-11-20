package com.trek.trekker.dto.request;

import com.trek.core.annotation.Required;
import com.trek.core.dto.request.ImageRequest;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrekkerStoryEditRequest implements Request {

    @Required(field = "id")
    private Long id;
    @Required(field = "Title")
    private String title;
    private String slug;
    private String shortDescription;
    private String content;
    private LocalDate completedAt;
    private Integer durationDays;
    private Boolean isDraft;
    private List<String> highlights;
    private List<String> tags;
    private Long destinationId;
    private List<ImageRequest> images;

}