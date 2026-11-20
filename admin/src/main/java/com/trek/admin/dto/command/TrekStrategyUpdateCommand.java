package com.trek.admin.dto.command;

import com.trek.core.constants.DifficultyLevel;
import com.trek.core.constants.RouteType;
import com.trek.core.dto.command.Command;
import com.trek.core.entity.TrekStrategy;
import lombok.Data;

import java.util.List;

@Data
public class TrekStrategyUpdateCommand implements Command {

    private TrekStrategy trekStrategy;
    private String name;
    private String slug;
    private String totalDays;
    private Integer totalDaysNumeric;
    private String acclimatizationDays;
    private String difficultyAdjustment;
    private String maxAltitude;
    private String totalDistance;
    private Double totalDistanceNumeric;
    private String aboutTrek;
    private String shortDescription;
    private String highestPoint;
    private String lowestPoint;
    private String accessPointCity;
    private String actualStartPoint;
    private String actualEndPoint;
    private RouteType routeType;
    private DifficultyLevel difficultyLevel;
    private List<String> highlights;

}