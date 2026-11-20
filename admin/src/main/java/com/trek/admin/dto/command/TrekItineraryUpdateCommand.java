package com.trek.admin.dto.command;

import com.trek.core.entity.TrekItinerary;
import com.trek.core.constants.DayType;
import com.trek.core.dto.command.Command;
import lombok.Data;

@Data
public class TrekItineraryUpdateCommand implements Command {

    private TrekItinerary trekItinerary;
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