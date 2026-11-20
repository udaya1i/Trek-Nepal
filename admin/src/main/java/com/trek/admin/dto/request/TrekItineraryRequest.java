package com.trek.admin.dto.request;

import com.trek.core.constants.DayType;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class TrekItineraryRequest implements Request {

    private Long strategyId;
    private String dayNumber;
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
    private DayType dayType;

}
