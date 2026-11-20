package com.trek.core.entity;

import com.trek.core.constants.DayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "trek_itineraries")
public class TrekItinerary extends BaseEntity {

    @Column(name = "day_number", nullable = false)
    private String dayNumber;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "end_location")
    private String endLocation;

    @Column(name = "distance_km")
    private String distanceKm;

    @Column(name = "distance_km_numeric")
    private Double distanceKmNumeric;

    @Column(name = "duration_hours")
    private String durationHours;

    @Column(name = "altitude_gain")
    private String altitudeGain;

    @Column(name = "altitude_loss")
    private String altitudeLoss;

    @Column(length = 2000, name = "short_description")
    private String shortDescription;

    @Column(length = 5000, name = "full_description")
    private String fullDescription;

    @Column(length = 1000, name = "flexibility_notes")
    private String flexibilityNotes;

    @JoinColumn(referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrekStrategy trekStrategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_type")
    private DayType dayType;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaypointStrategyItineraryMap> waypointMap;

}