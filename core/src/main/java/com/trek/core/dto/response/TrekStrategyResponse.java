package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.constants.RouteType;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TrekStrategyResponse {

    private Long id;
    private String name;
    private String slug;
    private String totalDays;
    private Integer totalDaysNumeric;
    private String shortDescription;
    private RouteType routeType;
    private String totalDistance;
    private Double totalDistanceNumeric;

    public static List<TrekStrategyResponse> from(List<TrekStrategy> strategy) {
        return strategy.stream().map(TrekStrategyResponse::from).toList();
    }

    public static TrekStrategyResponse from(TrekStrategy strategy) {
        if (strategy == null) {
            return null;
        }
        return TrekStrategyResponse.builder()
                .id(strategy.getId())
                .name(strategy.getName())
                .slug(strategy.getSlug())
                .totalDays(strategy.getTotalDays())
                .totalDaysNumeric(strategy.getTotalDaysNumeric())
                .routeType(strategy.getRouteType())
                .shortDescription(strategy.getShortDescription())
                .totalDistance(strategy.getTotalDistance())
                .totalDistanceNumeric(strategy.getTotalDistanceNumeric())
                 .build();
    }
}