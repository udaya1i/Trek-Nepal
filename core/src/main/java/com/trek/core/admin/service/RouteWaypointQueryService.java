package com.trek.core.admin.service;

import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import com.trek.core.repository.RouteWaypointRepository;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.WaypointStrategyItineraryMapRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RouteWaypointQueryService {

    private RouteWaypointRepository routeWaypointRepository;
    private WaypointStrategyItineraryMapRepository waypointStrategyItineraryMapRepository;

    @Transactional(readOnly = true)
    public TrekWaypoint getById(Long id) {
        return routeWaypointRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid route waypoint"));
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> findByDestination(Destination destination) {
        return routeWaypointRepository.findByDestination(destination);
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> findByStrategy(TrekStrategy strategy) {
        return waypointStrategyItineraryMapRepository.findByStrategy(strategy);
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> findByItinerary(TrekItinerary itinerary) {
        return waypointStrategyItineraryMapRepository.findByItinerary(itinerary);
    }
}