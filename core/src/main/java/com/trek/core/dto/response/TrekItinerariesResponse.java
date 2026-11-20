package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.entity.TrekItinerary;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TrekItinerariesResponse {

    private Long id;
    private String dayNumber;
    private String startLocation;
    private String endLocation;
    private String durationHours;
    private String altitudeGain;
    private String altitudeLoss;
    private String shortDescription;
    private String fullDescription;
    private String temperature;

    public static List<TrekItinerariesResponse> of(List<TrekItinerary> itinerary) {
        return itinerary.stream().map(TrekItinerariesResponse::of).toList();
    }

    public static TrekItinerariesResponse of(TrekItinerary itinerary) {
        TrekItinerariesResponse response = new TrekItinerariesResponse();
        response.setId(itinerary.getId());
        response.setShortDescription(itinerary.getShortDescription());
        response.setDayNumber(itinerary.getDayNumber());
        response.setStartLocation(itinerary.getStartLocation());
        response.setEndLocation(itinerary.getEndLocation());
        response.setDurationHours(itinerary.getDurationHours());
        response.setAltitudeGain(itinerary.getAltitudeGain());
        response.setAltitudeLoss(itinerary.getAltitudeLoss());
        response.setFullDescription(itinerary.getFullDescription());
        return response;
    }
}
