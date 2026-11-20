package com.trek.admin.dto.request;

import com.trek.core.constants.WaypointType;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class TrekWaypointAddRequest implements Request {

    private Integer sequenceOrder;
    private String name;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String altitude;
    private Integer altitudeNumeric;
    private String shortDescription;
    private String fullDescription;
    private Boolean hasWifi;
    private Boolean hasCharging;
    private Boolean hasHotShower;
    private Boolean hasAtm;
    private WaypointType type;
    private Long strategy;
    private Long destination;
    private Long trekItinerary;
    private List<String> significance;
    private List<String> images;
}
