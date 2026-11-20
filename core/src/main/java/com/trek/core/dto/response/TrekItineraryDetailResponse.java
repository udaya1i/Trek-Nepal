package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.entity.TrekItinerary;
import com.trek.core.constants.DayType;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TrekItineraryDetailResponse {


    private Long id;
    private String dayNumber;
    private DayType dayType;
    private String startLocation;
    private String endLocation;
    private String distanceKm;
    private Double distanceKmNumeric;
    private String durationHours;
    private String altitudeGain;
    private String altitudeLoss;
    private String shortDescription;
    private String fullDescription;

     private String flexibilityNotes;

    public static List<TrekItineraryDetailResponse> of(List<TrekItinerary> itineraries) {
        return itineraries.stream().map(TrekItineraryDetailResponse::of).toList();
    }

    public static TrekItineraryDetailResponse of(TrekItinerary itinerary) {
        TrekItineraryDetailResponse response = new TrekItineraryDetailResponse();

        response.setId(itinerary.getId());
        response.setDayNumber(itinerary.getDayNumber());
        response.setDayType(itinerary.getDayType());
        response.setStartLocation(itinerary.getStartLocation());
        response.setEndLocation(itinerary.getEndLocation());
        response.setDistanceKm(itinerary.getDistanceKm());
        response.setDistanceKmNumeric(itinerary.getDistanceKmNumeric());
        response.setDurationHours(itinerary.getDurationHours());
        response.setAltitudeGain(itinerary.getAltitudeGain());
        response.setAltitudeLoss(itinerary.getAltitudeLoss());
        response.setShortDescription(itinerary.getShortDescription());
        response.setFullDescription(itinerary.getFullDescription());
        response.setFlexibilityNotes(itinerary.getFlexibilityNotes());

        return response;
    }

}
