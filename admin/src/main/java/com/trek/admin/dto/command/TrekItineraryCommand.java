package com.trek.admin.dto.command;

import com.trek.core.entity.TrekStrategy;
import com.trek.core.constants.DayType;
import com.trek.core.dto.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class TrekItineraryCommand implements Command {

    private TrekStrategy strategy;
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
