package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.constants.DifficultyLevel;
import com.trek.core.constants.RouteType;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TrekStrategyDetailResponse(
        Long id,
        String name,
        String slug,
        String totalDays,
        Integer totalDaysNumeric,
        String acclimatizationDays,
        String difficultyAdjustment,
         String maxAltitude,
        String totalDistance,
        Double totalDistanceNumeric,
        String aboutTrek,
        String shortDescription,
        String highestPoint,
        String lowestPoint,
        String accessPointCity,
        String actualStartPoint,
        String actualEndPoint,
        RouteType routeType,
        DifficultyLevel difficultyLevel,
        List<String> highlights) {

    public static TrekStrategyDetailResponse toResponse(TrekStrategy strategy) {
        return new TrekStrategyDetailResponse(
                strategy.getId(),
                strategy.getName(),
                strategy.getSlug(),
                strategy.getTotalDays(),
                strategy.getTotalDaysNumeric(),
                strategy.getAcclimatizationDays(),
                strategy.getDifficultyAdjustment(),
                 strategy.getMaxAltitude(),
                strategy.getTotalDistance(),
                strategy.getTotalDistanceNumeric(),
                strategy.getAboutTrek(),
                strategy.getShortDescription(),
                strategy.getHighestPoint(),
                strategy.getLowestPoint(),
                strategy.getAccessPointCity(),
                strategy.getActualStartPoint(),
                strategy.getActualEndPoint(),
                strategy.getRouteType(),
                strategy.getDifficultyLevel(),
                strategy.getHighlights()
        );
    }
}
