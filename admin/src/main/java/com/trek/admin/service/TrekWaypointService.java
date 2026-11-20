package com.trek.admin.service;

import com.trek.admin.dto.command.WayPointAddCommand;
import com.trek.admin.dto.command.WayPointUpdateCommand;
import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.admin.service.RouteWaypointQueryService;
import com.trek.core.admin.service.SlugAssignmentService;
import com.trek.core.admin.service.TrekItineraryQueryService;
import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.constants.SluggableType;
import com.trek.core.entity.*;
import com.trek.core.repository.RouteWaypointRepository;
import com.trek.core.repository.WaypointStrategyItineraryMapRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekWaypointService {

    private final RouteWaypointRepository routeWaypointRepository;
    private final MediaMappingService mediaMappingService;
    private final RouteWaypointQueryService routeWaypointQueryService;
    private final DestinationQueryService destinationQueryService;
    private final TrekStrategyQueryService trekStrategyQueryService;
    private final TrekItineraryQueryService trekItineraryQueryService;
    private final SlugAssignmentService slugAssignmentService;
    private final WaypointStrategyItineraryMapRepository waypointMapRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public TrekWaypoint save(WayPointAddCommand command) {

        TrekWaypoint waypoint = new TrekWaypoint();
        waypoint.setDestination(command.getDestination());
        waypoint.setName(command.getName());
        waypoint.setLatitude(command.getLatitude());
        waypoint.setLatitudeNumeric(command.getLatitudeNumeric());
        waypoint.setLongitude(command.getLongitude());
        waypoint.setLongitudeNumeric(command.getLongitudeNumeric());
        waypoint.setAltitude(command.getAltitude());
        waypoint.setAltitudeNumeric(command.getAltitudeNumeric());
        waypoint.setType(command.getType());
        waypoint.setShortDescription(command.getShortDescription());
        waypoint.setFullDescription(command.getFullDescription());
        waypoint.setHasWifi(command.getHasWifi());
        waypoint.setHasCharging(command.getHasCharging());
        waypoint.setHasHotShower(command.getHasHotShower());
        waypoint.setHasAtm(command.getHasAtm());
        waypoint.setAtmNotes(command.getAtmNotes());
        waypoint.setSignificance(command.getSignificance());
        waypoint.setSequenceOrder(command.getSequenceOrder());
        waypoint.setSlug(slugAssignmentService.ensureUniqueSlug(command.getName(), routeWaypointRepository::existsBySlug));
        TrekWaypoint savedWaypoint = routeWaypointRepository.save(waypoint);
        mediaMappingService.map(command.getImages(), savedWaypoint);
        return savedWaypoint;
    }

    @Transactional
    public TrekWaypoint update(WayPointUpdateCommand command) {
        TrekWaypoint waypoint = command.getTrekWaypoint();
        waypoint.setName(command.getName());
        waypoint.setLatitude(command.getLatitude());
        waypoint.setLatitudeNumeric(command.getLatitudeNumeric());
        waypoint.setLongitude(command.getLongitude());
        waypoint.setLongitudeNumeric(command.getLongitudeNumeric());
        waypoint.setAltitude(command.getAltitude());
        waypoint.setAltitudeNumeric(command.getAltitudeNumeric());
        waypoint.setType(command.getType());
        waypoint.setShortDescription(command.getShortDescription());
        waypoint.setFullDescription(command.getFullDescription());
        waypoint.setHasWifi(command.getHasWifi());
        waypoint.setHasCharging(command.getHasCharging());
        waypoint.setHasHotShower(command.getHasHotShower());
        waypoint.setHasAtm(command.getHasAtm());
        waypoint.setAtmNotes(command.getAtmNotes());
        waypoint.setSignificance(command.getSignificance());
        waypoint.setSequenceOrder(command.getSequenceOrder());
        waypoint.setSlug(slugAssignmentService.resolveSlugOnUpdate(SluggableType.WAYPOINT, waypoint.getId(),
                waypoint.getSlug(), command.getSlug(), waypoint.getName(), routeWaypointRepository::existsBySlug));
        TrekWaypoint savedWaypoint = routeWaypointRepository.save(waypoint);
        if (command.getImages() != null) {
            mediaMappingService.map(command.getImages(), savedWaypoint);
        }
        return savedWaypoint;
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> getByDestination(Long id) {
        Destination destination = destinationQueryService.getById(id);
        return routeWaypointQueryService.findByDestination(destination);
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> getAllByDestination(Long id) {
        Destination destination = destinationQueryService.getById(id);
        return routeWaypointQueryService.findByDestination(destination);
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> getByItineraries(TrekItinerary itinerary) {
        return routeWaypointQueryService.findByItinerary(itinerary);
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> getByStrategy(Long id) {
        TrekStrategy trekStrategy = trekStrategyQueryService.getById(id);
        return routeWaypointQueryService.findByStrategy(trekStrategy);
    }

    @Transactional(readOnly = true)
    public TrekWaypoint getById(Long id) {
        return routeWaypointQueryService.getById(id);
    }

    @Transactional
    public TrekWaypoint assignToStrategy(Long waypointId, Long strategyId) {
        TrekWaypoint waypoint = routeWaypointQueryService.getById(waypointId);
        TrekStrategy strategy = trekStrategyQueryService.getById(strategyId);

        boolean alreadyLinked = waypointMapRepository
                .findByWaypointIdAndStrategyId(waypointId, strategyId)
                .isPresent();

        if (!alreadyLinked) {
            WaypointStrategyItineraryMap map = new WaypointStrategyItineraryMap();
            map.setWaypoint(waypoint);
            map.setStrategy(strategy);
            waypointMapRepository.save(map);
        }

        return waypoint;
    }

    @Transactional
    public TrekWaypoint assignToItinerary(Long waypointId, Long itineraryId) {
        TrekWaypoint waypoint = routeWaypointQueryService.getById(waypointId);
        TrekItinerary itinerary = trekItineraryQueryService.getById(itineraryId);
        TrekStrategy strategy = itinerary.getTrekStrategy();

        WaypointStrategyItineraryMap map = waypointMapRepository
                .findByWaypointIdAndStrategyId(waypointId, strategy.getId())
                .orElseGet(() -> {
                    WaypointStrategyItineraryMap m = new WaypointStrategyItineraryMap();
                    m.setWaypoint(waypoint);
                    m.setStrategy(strategy);
                    return m;
                });

        map.setItinerary(itinerary);
        waypointMapRepository.save(map);

        return waypoint;
    }

}