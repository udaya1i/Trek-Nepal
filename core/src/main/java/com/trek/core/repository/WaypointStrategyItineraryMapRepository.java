package com.trek.core.repository;

import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import com.trek.core.entity.WaypointStrategyItineraryMap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WaypointStrategyItineraryMapRepository extends AbstractRepository<WaypointStrategyItineraryMap, Long>{

    Optional<WaypointStrategyItineraryMap> findByWaypointIdAndStrategyId(Long waypointId, Long strategyId);

    @Query("select distinct m.waypoint from WaypointStrategyItineraryMap m where m.strategy = :strategy")
    List<TrekWaypoint> findAllByStrategy(@Param("strategy") TrekStrategy strategy);

    @Query("select distinct m.waypoint from WaypointStrategyItineraryMap m where m.itinerary = :itinerary")
    List<TrekWaypoint> findAllByTrekItinerary(@Param("itinerary") TrekItinerary trekItinerary);

    @Query("select distinct m.waypoint from WaypointStrategyItineraryMap m where m.strategy = :strategy")
    List<TrekWaypoint> findByStrategy(TrekStrategy strategy);

    @Query("select distinct m.waypoint from WaypointStrategyItineraryMap m where m.itinerary = :itinerary")
    List<TrekWaypoint> findByItinerary(TrekItinerary itinerary);



}
