package com.trek.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "waypoint_strategy_itinerary_map")
public class WaypointStrategyItineraryMap extends BaseEntity {

    @JoinColumn(name = "waypoint_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TrekWaypoint waypoint;

    @JoinColumn(name = "strategy_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TrekStrategy strategy;

    @JoinColumn(name = "itinerary_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrekItinerary itinerary;

    @Column(name = "sequence_order")
    private Integer sequenceOrder;
}