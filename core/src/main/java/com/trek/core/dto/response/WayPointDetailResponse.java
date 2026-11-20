package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.constants.WaypointType;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekWaypoint;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WayPointDetailResponse {

    private Long id;
    private String name;
    private String slug;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String altitude;
    private Integer altitudeNumeric;
    private WaypointType type;
    private String description;
    private String shortDescription;
    private String fullDescription;
    private List<ImageResponse> images;
    private List<String> significant;
    private Boolean hasWifi;
    private Boolean hasCharging;
    private Boolean hasHotShower;
    private Boolean hasAtm;
    private String atmNotes;
    private Long views = 0l;

    public static WayPointDetailResponse of(TrekWaypoint waypoint, List<MediaMapping> mapping) {
        WayPointDetailResponse response = new WayPointDetailResponse();
        response.setId(waypoint.getId());
        response.setName(waypoint.getName());
        response.setSlug(waypoint.getSlug());
        response.setLatitude(waypoint.getLatitude());
        response.setLatitudeNumeric(waypoint.getLatitudeNumeric());
        response.setLongitude(waypoint.getLongitude());
        response.setLongitudeNumeric(waypoint.getLongitudeNumeric());
        response.setSignificant(waypoint.getSignificance());
        response.setHasWifi(waypoint.getHasWifi());
        response.setHasCharging(waypoint.getHasCharging());
        response.setViews(waypoint.getViews());
        response.setHasHotShower(waypoint.getHasHotShower());
        response.setHasAtm(waypoint.getHasAtm());
        response.setAtmNotes(waypoint.getAtmNotes());
        response.setAltitude(waypoint.getAltitude());
        response.setAltitudeNumeric(waypoint.getAltitudeNumeric());
        response.setFullDescription(waypoint.getFullDescription());
        response.setShortDescription(waypoint.getShortDescription());
        response.setType(waypoint.getType());
        response.setImages(ImageResponse.of(mapping));
        return response;
    }
}
