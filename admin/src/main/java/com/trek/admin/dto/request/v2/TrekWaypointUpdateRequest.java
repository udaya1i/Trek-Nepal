package com.trek.admin.dto.request.v2;

import com.trek.core.constants.WaypointType;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class TrekWaypointUpdateRequest implements Request {

    private Long id;
    private Integer sequenceOrder;
    private String name;
    private String slug;
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
    private String atmNotes;
    private WaypointType type;
    private List<String> significance;
    private List<String> images;
}