package com.trek.admin.dto.command;

import com.trek.core.constants.DestinationType;
import com.trek.core.constants.TrailCategory;
import com.trek.core.dto.command.Command;
import com.trek.core.entity.District;
import com.trek.core.entity.LocalLevel;
import com.trek.core.entity.Media;
import com.trek.core.entity.Province;
import lombok.Data;

import java.util.List;

@Data
public class DestinationAddCommand implements Command {


    private String name;
    private DestinationType destinationType;
    private String altitude;
    private Integer altitudeNumeric;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String description;
    private List<String> highlights;
    private Province province;
    private District district;
    private LocalLevel localLevel;
    private String primaryAccessCity;
    private String distanceFromAccessCity;
    private String region;
    private List<Media> images;
    private List<TrailCategory> trailCategories;
}
