package com.trek.core.repository;

import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteWaypointRepository extends AbstractRepository<TrekWaypoint, Long> {

    List<TrekWaypoint> findByDestination(Destination destination);


    Optional<TrekWaypoint> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<TrekWaypoint> findBySlugIsNull();
}