package com.trek.admin.dto.request.v2;

import com.trek.core.annotation.Required;
import com.trek.core.constants.DestinationType;
import com.trek.core.constants.TrailCategory;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class DestinationUpdateRequest implements Request {

    @Required(field = "id")
    private Long id;
    private String name;
    private String slug;
    private String altitude;
    private Integer altitudeNumeric;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String description;
    private String region;
    private String primaryAccessCity;
    private String distanceFromAccessCity;
    private DestinationType destinationType;
    private List<TrailCategory> trailCategories;
    private List<String> highlights;
    private Long province;
    private Long district;
    private Long localLevel;
    private List<String> images;

}
