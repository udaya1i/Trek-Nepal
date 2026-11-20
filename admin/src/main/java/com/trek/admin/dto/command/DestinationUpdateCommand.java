package com.trek.admin.dto.command;

import com.trek.core.constants.DestinationType;
import com.trek.core.constants.TrailCategory;
import com.trek.core.dto.command.Command;
import com.trek.core.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class DestinationUpdateCommand implements Command {

    private Destination destination;
    private String description;
    private String name;
    private String slug;
    private String altitude;
    private Integer altitudeNumeric;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String region;
    private String primaryAccessCity;
    private String distanceFromAccessCity;
    private DestinationType destinationType;
    private List<TrailCategory> trailCategories;
    private List<String> highlights;
    private Province province;
    private District district;
    private LocalLevel localLevel;
    private List<Media> images;

}
