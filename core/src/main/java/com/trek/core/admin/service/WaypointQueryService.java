package com.trek.core.admin.service;

import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.WayPointRepository;
import com.trek.core.repository.WaypointStrategyItineraryMapRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WaypointQueryService {

    private final WayPointRepository wayPointRepository;
    private final WaypointStrategyItineraryMapRepository waypointStrategyItineraryMapRepository;

    public TrekWaypoint getById(Long id) {
        return wayPointRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid waypoint"));
    }

    public List<TrekWaypoint> getByStrategy(TrekStrategy strategy) {
        return waypointStrategyItineraryMapRepository.findAllByStrategy(strategy);
    }

    public List<TrekWaypoint> getByItinerary(TrekItinerary itinerary) {
        return waypointStrategyItineraryMapRepository.findAllByTrekItinerary(itinerary);
    }
}