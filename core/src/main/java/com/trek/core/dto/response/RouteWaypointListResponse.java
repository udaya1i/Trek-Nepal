package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.constants.WaypointType;
import com.trek.core.entity.TrekWaypoint;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RouteWaypointListResponse {

    private Long id;
    private Integer sequenceOrder;
    private String name;
    private WaypointType type;
    private String altitude;
    private Integer altitudeNumeric;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String shortDescription;
    private Long views;


    public static List<RouteWaypointListResponse> of(List<TrekWaypoint> rw) {
        return rw.stream().map(RouteWaypointListResponse::of).toList();
    }

    public static RouteWaypointListResponse of(TrekWaypoint w) {
        RouteWaypointListResponse res = new RouteWaypointListResponse();

        res.setSequenceOrder(w.getSequenceOrder());
        res.setId(w.getId());
        res.setName(w.getName());
        res.setType(w.getType());
        res.setAltitude(w.getAltitude());
        res.setAltitudeNumeric(w.getAltitudeNumeric());
        res.setLatitude(w.getLatitude());
        res.setLatitudeNumeric(w.getLatitudeNumeric());
        res.setLongitude(w.getLongitude());
        res.setLongitudeNumeric(w.getLongitudeNumeric());
        res.setShortDescription(w.getShortDescription());
        res.setViews(w.getViews());

        return res;
    }
}
