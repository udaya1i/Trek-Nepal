package com.trek.trekker.dto.response;

import com.trek.core.constants.DestinationType;
import com.trek.core.dto.response.DestinationRatingStatusResponse;
import com.trek.core.dto.response.ImageResponse;
import com.trek.core.entity.*;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class PublicDestinationResponse {

    private Long id;
    private String name;
    private String slug;
    private DestinationType destinationType;
    private String altitude;
    private Integer altitudeNumeric;
    private String latitude;
    private Double latitudeNumeric;
    private String longitude;
    private Double longitudeNumeric;
    private String description;
    private String province;
    private String district;
    private String localLevel;
    private String primaryAccessCity;
    private String distanceFromAccessCity;
    private List<ImageResponse> images;
    private List<String> highlights;
    private Long views;
    private ReviewStatusResponse reviews;


    public static PublicDestinationResponse of(Destination destination, List<MediaMapping> images, DestinationRatingStatusResponse reviewStatus) {

        PublicDestinationResponse response = new PublicDestinationResponse();
        response.setReviews(ReviewStatusResponse.from(reviewStatus));
        response.setId(destination.getId());
        response.setName(destination.getName());
        response.setSlug(destination.getSlug());
        response.setImages(ImageResponse.of(images));
        response.setDestinationType(destination.getDestinationType());
        response.setAltitude(destination.getAltitude());
        response.setAltitudeNumeric(destination.getAltitudeNumeric());
        response.setLatitude(destination.getLatitude());
        response.setLatitudeNumeric(destination.getLatitudeNumeric());
        response.setLongitude(destination.getLongitude());
        response.setLongitudeNumeric(destination.getLongitudeNumeric());
        response.setDescription(destination.getDescription());
        response.setHighlights(destination.getHighlights());
        response.setViews(destination.getViews());

        response.setProvince(
                Optional.ofNullable(destination.getProvince())
                        .map(Province::getName)
                        .orElse(null)
        );
        response.setDistrict(getSafeName(destination.getDistrict()));
        response.setLocalLevel(getSafeName(destination.getLocalLevel()));

        response.setPrimaryAccessCity(destination.getPrimaryAccessCity());
        response.setDistanceFromAccessCity(destination.getDistanceFromAccessCity());

        return response;
    }

    private static String getSafeName(Object entity) {
        if (entity == null) return null;

        if (entity instanceof Province p) return p.getName();
        if (entity instanceof District d) return d.getName();
        if (entity instanceof LocalLevel l) return l.getName();

        return null;
    }

}