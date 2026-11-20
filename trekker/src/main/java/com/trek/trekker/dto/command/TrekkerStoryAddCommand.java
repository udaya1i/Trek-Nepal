package com.trek.trekker.dto.command;

import com.trek.core.dto.command.Command;
import com.trek.core.entity.Destination;
import com.trek.core.entity.Media;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrekkerStoryAddCommand implements Command {

    private String title;
    private String shortDescription;
    private String content;
    private LocalDate completedAt;
    private Integer durationDays;
    private List<String> highlights;
    private List<String> tags;
    private Destination destination;
     private List<Media> images;
}
