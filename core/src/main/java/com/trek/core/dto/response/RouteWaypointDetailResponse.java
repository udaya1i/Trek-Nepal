package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.constants.WaypointType;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekWaypoint;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteWaypointDetailResponse {

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
    private String atmNotes;
    private WaypointType type;
    private List<String> significance;
    private Long views;
    private List<ImageResponse> images;

    public static RouteWaypointDetailResponse toResponse(TrekWaypoint waypoint, List<MediaMapping> images) {
        RouteWaypointDetailResponse response = new RouteWaypointDetailResponse();

        response.setSequenceOrder(waypoint.getSequenceOrder());
        response.setName(waypoint.getName());
        response.setLatitude(waypoint.getLatitude());
        response.setLatitudeNumeric(waypoint.getLatitudeNumeric());
        response.setLongitude(waypoint.getLongitude());
        response.setLongitudeNumeric(waypoint.getLongitudeNumeric());
        response.setAltitude(waypoint.getAltitude());
        response.setAltitudeNumeric(waypoint.getAltitudeNumeric());
        response.setShortDescription(waypoint.getShortDescription());
        response.setFullDescription(waypoint.getFullDescription());
        response.setHasWifi(waypoint.getHasWifi());
        response.setHasCharging(waypoint.getHasCharging());
        response.setHasHotShower(waypoint.getHasHotShower());
        response.setHasAtm(waypoint.getHasAtm());
        response.setAtmNotes(waypoint.getAtmNotes());
        response.setType(waypoint.getType());
        response.setSignificance(waypoint.getSignificance());
        response.setViews(waypoint.getViews());
        response.setImages(ImageResponse.of(images));

        return response;
    }


}
