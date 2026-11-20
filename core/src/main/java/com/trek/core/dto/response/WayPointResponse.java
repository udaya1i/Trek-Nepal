package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.constants.WaypointType;
import com.trek.core.entity.Media;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekWaypoint;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WayPointResponse {

    private Long id;
    private String name;
    private String slug;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String shortDescription;
    private String altitude;
    private Integer altitudeNumeric;
    private WaypointType type;
    private List<ImageResponse> images;

    public static List<WayPointResponse> of(List<TrekWaypoint> waypoint) {
        return waypoint.stream().map(WayPointResponse::of).toList();
    }

    public static WayPointResponse of(TrekWaypoint waypoint) {
        WayPointResponse response = new WayPointResponse();
        response.setName(waypoint.getName());
        response.setSlug(waypoint.getSlug());
        response.setLatitude(waypoint.getLatitude());
        response.setLatitudeNumeric(waypoint.getLatitudeNumeric());
        response.setLongitude(waypoint.getLongitude());
        response.setLongitudeNumeric(waypoint.getLongitudeNumeric());
        response.setAltitude(waypoint.getAltitude());
        response.setAltitudeNumeric(waypoint.getAltitudeNumeric());
        response.setType(waypoint.getType());
        response.setId(waypoint.getId());
        response.setShortDescription(waypoint.getShortDescription());
        return response;
    }

    public static WayPointResponse of(TrekWaypoint waypoint, List<MediaMapping> images) {
        WayPointResponse response = new WayPointResponse();
        response.setName(waypoint.getName());
        response.setImages(ImageResponse.of(images));
        response.setLatitude(waypoint.getLatitude());
        response.setLatitudeNumeric(waypoint.getLatitudeNumeric());
        response.setLongitude(waypoint.getLongitude());
        response.setLongitudeNumeric(waypoint.getLongitudeNumeric());
        response.setAltitude(waypoint.getAltitude());
        response.setAltitudeNumeric(waypoint.getAltitudeNumeric());
        response.setType(waypoint.getType());
        response.setId(waypoint.getId());
        response.setShortDescription(waypoint.getShortDescription());
        return response;
    }

}
