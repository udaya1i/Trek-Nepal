package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.constants.DestinationType;
import com.trek.core.entity.Destination;
import com.trek.core.entity.MediaMapping;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinationDetailResponse {

    private Long id;
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
    private String province;
    private String district;
    private String localLevel;
    private String primaryAccessCity;
    private String distanceFromAccessCity;
    private String region;
    private List<ImageResponse> images;
    private Long views;

    public static DestinationDetailResponse of(Destination destination, List<MediaMapping> images) {

        DestinationDetailResponse response = new DestinationDetailResponse();
        response.setId(destination.getId());
        response.setName(destination.getName());
        response.setDestinationType(destination.getDestinationType());
        response.setAltitude(destination.getAltitude());
        response.setAltitudeNumeric(destination.getAltitudeNumeric());
        response.setImages(ImageResponse.of(images));
        response.setLatitude(destination.getLatitude());
        response.setLatitudeNumeric(destination.getLatitudeNumeric());
        response.setLongitude(destination.getLongitude());
        response.setLongitudeNumeric(destination.getLongitudeNumeric());
        response.setDescription(destination.getDescription());
        response.setHighlights(destination.getHighlights());
        response.setRegion(destination.getRegion());
        response.setProvince(destination.getProvince().getName());
        response.setDistrict(destination.getDistrict().getName());
        response.setLocalLevel(destination.getLocalLevel().getName());
        response.setPrimaryAccessCity(destination.getPrimaryAccessCity());
        response.setDistanceFromAccessCity(destination.getDistanceFromAccessCity());
        response.setViews(destination.getViews());
        return response;

    }

}
