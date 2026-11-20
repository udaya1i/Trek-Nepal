package com.trek.admin.dto.command;

import com.trek.core.constants.WaypointType;
import com.trek.core.dto.command.Command;
import com.trek.core.entity.Media;
import com.trek.core.entity.TrekWaypoint;
import lombok.Data;

import java.util.List;

@Data
public class WayPointUpdateCommand implements Command {

    private TrekWaypoint trekWaypoint;
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
    private List<Media> images;

}