package com.trek.core.entity;

import com.trek.core.constants.DifficultyLevel;
import com.trek.core.constants.RouteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "trek_strategies")
public class TrekStrategy extends BaseEntity {

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "total_days", nullable = false)
    private String totalDays;

    @Column(name = "total_days_numeric")
    private Integer totalDaysNumeric;

    @Column(name = "acclimatization_days")
    private String acclimatizationDays;

    @Column(name = "difficulty_adjustment", length = 5000)
    private String difficultyAdjustment;

    @Column(name = "max_altitude")
    private String maxAltitude;

    @Column(name = "total_distance")
    private String totalDistance;

    @Column(name = "total_distance_numeric")
    private Double totalDistanceNumeric;

    @Column(length = 20000, name = "about_trek")
    private String aboutTrek;

    @Column(name = "short_description", length = 5000)
    private String shortDescription;

    @Column(name = "highest_point")
    private String highestPoint;

    @Column(name = "lowest_point")
    private String lowestPoint;

    @Column(name = "access_point_city")
    private String accessPointCity;

    @Column(name = "actual_start_point")
    private String actualStartPoint;

    @Column(name = "actual_end_point")
    private String actualEndPoint;

    @Column(name = "views")
    private Long views = 0l;

    @Enumerated(EnumType.STRING)
    @Column(name = "route_type")
    private RouteType routeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Destination destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private DifficultyLevel difficultyLevel;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trek_highlights", joinColumns = @JoinColumn(name = "trek_id"))
    @Column(name = "highlight", length = 5000)
    private List<String> highlights;

    @OneToMany(mappedBy = "trekStrategy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrekItinerary> itineraries;

    @OneToMany(mappedBy = "trekStrategy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrekPacking> packings;

    @OneToMany(mappedBy = "strategy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaypointStrategyItineraryMap> waypointMap;
    


}