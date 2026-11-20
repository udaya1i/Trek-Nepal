package com.trek.core.entity;

import com.trek.core.constants.DestinationStatus;
import com.trek.core.constants.DestinationType;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.constants.TrailCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "destinations")
public class Destination extends BaseEntity implements MediaOwner {

    @Column(nullable = false)
    private String name;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "altitude_numeric")
    private Integer altitudeNumeric;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "latitude_numeric")
    private Double latitudeNumeric;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "longitude_numeric")
    private Double longitudeNumeric;

    @Column(length = 50000, name = "description")
    private String description;

    @Column(name = "region")
    private String region;

    @Column(name = "primary_access_city")
    private String primaryAccessCity;

    @Column(name = "distance_from_access_city")
    private String distanceFromAccessCity;

    @Column(name = "best_season")
    private String bestSeason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Province province;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private LocalLevel localLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "destination_type")
    private DestinationType destinationType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "destination_trail_environments", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "trail_environment")
    @Enumerated(EnumType.STRING)
    private List<TrailCategory> trailCategory = new ArrayList<>();

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrekStrategy> trekStrategy = new ArrayList<>();

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Permit> permits = new ArrayList<>();

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrekWaypoint> waypoints = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destination_highlights", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "highlight", length = 2000)
    private List<String> highlights = new ArrayList<>();

    @Column(name = "views")
    private Long views = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DestinationStatus destinationStatus;

    @Override
    public MediaOwnerType getOwnerType() {
        return MediaOwnerType.DESTINATION;
    }
}