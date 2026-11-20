package com.trek.core.entity;

import com.trek.core.constants.MediaOwnerType;
import com.trek.core.constants.WaypointType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "route_waypoints")
public class TrekWaypoint extends BaseEntity implements MediaOwner {

    @Column(name = "sequence_order")
    private Integer sequenceOrder;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "latitude_numeric")
    private Double latitudeNumeric;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "longitude_numeric")
    private Double longitudeNumeric;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "altitude_numeric")
    private Integer altitudeNumeric;

    @Column(length = 5000, name = "short_description")
    private String shortDescription;

    @Column(length = 10000, name = "full_description")
    private String fullDescription;

    @Column(name = "has_wifi")
    private Boolean hasWifi;

    @Column(name = "has_charging")
    private Boolean hasCharging;

    @Column(name = "has_hot_shower")
    private Boolean hasHotShower;

    @Column(name = "has_atm")
    private Boolean hasAtm;

    @Column(name = "atm_notes")
    private String atmNotes;

    @Column(name = "views")
    private Long views = 0l;

    @Enumerated(EnumType.STRING)
    @Column(name = "waypoint_type")
    private WaypointType type;

    @JoinColumn(name = "destination_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Destination destination;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "waypoint_significance", joinColumns = @JoinColumn(name = "waypoint_id"))
    @Column(name = "significance", length = 5000)
    private List<String> significance;

    @Override
    public MediaOwnerType getOwnerType() {
        return MediaOwnerType.WAYPOINT;
    }
}