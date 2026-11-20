package com.trek.admin.dto.request.v2;

import com.trek.core.annotation.Required;
import com.trek.core.constants.DifficultyLevel;
import com.trek.core.constants.RouteType;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class TrekStrategyAddRequest implements Request {

    @Required(field = "Destination")
    private Long destinationId;

    private String name;
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
