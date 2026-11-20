package com.trek.trekker.dto.request;

import com.trek.core.dto.request.ImageDeleteRequest;
import com.trek.core.dto.request.ImageRequest;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrekkerStoryAddRequest implements Request {

    private String title;
    private String shortDescription;
    private String content;
    private LocalDate completedAt;
    private Integer durationDays;
    private List<String> highlights;
    private List<String> tags;
    private List<ImageRequest> images;
    private Long destination;

}
