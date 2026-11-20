package com.trek.admin.facade;

import com.trek.admin.dto.command.WayPointAddCommand;
import com.trek.admin.dto.command.WayPointUpdateCommand;
import com.trek.admin.dto.request.TrekWaypointAddRequest;
import com.trek.admin.dto.request.requestValidator.TrekWaypointAddRequestValidator;
import com.trek.admin.dto.request.requestValidator.TrekWaypointUpdateRequestValidator;
import com.trek.admin.dto.request.v2.TrekWaypointUpdateRequest;
import com.trek.admin.service.TrekWaypointService;
import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.admin.service.TrekItineraryQueryService;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.dto.response.RouteWaypointDetailResponse;
import com.trek.core.dto.response.RouteWaypointListResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.WayPointResponse;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekWaypoint;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekWayPointFacade {

    private final TrekWaypointAddRequestValidator validator;
    private final TrekWaypointUpdateRequestValidator updateValidator;
    private final TrekWaypointService trekWaypointService;
    private final TrekItineraryQueryService trekItineraryQueryService;
    private final MediaMappingService mediaMappingService;

    public ServerResponse<WayPointResponse> add(TrekWaypointAddRequest request) {
        WayPointAddCommand command = validator.validate(request);
        TrekWaypoint waypoint = trekWaypointService.save(command);
        return ServerResponse.<WayPointResponse>builder()
                .message("Waypoint saved successfully")
                .status(HttpStatus.CREATED)
                .data(WayPointResponse.of(waypoint))
                .build();
    }

    public ServerResponse<WayPointResponse> update(TrekWaypointUpdateRequest request) {
        WayPointUpdateCommand command = updateValidator.validate(request);
        TrekWaypoint waypoint = trekWaypointService.update(command);
        return ServerResponse.<WayPointResponse>builder()
                .message("Waypoint updated successfully")
                .status(HttpStatus.OK)
                .data(WayPointResponse.of(waypoint))
                .build();
    }

    public ServerResponse<List<RouteWaypointListResponse>> getByDestination(Long trekId) {
        return ServerResponse.<List<RouteWaypointListResponse>>builder()
                .message("Waypoint(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(RouteWaypointListResponse.of(trekWaypointService.getByDestination(trekId)))
                .build();
    }

    public ServerResponse<List<RouteWaypointListResponse>> getAllByDestination(Long trekId) {
        return ServerResponse.<List<RouteWaypointListResponse>>builder()
                .message("Waypoint(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(RouteWaypointListResponse.of(trekWaypointService.getAllByDestination(trekId)))
                .build();
    }

    public ServerResponse<List<RouteWaypointListResponse>> getByItineraries(Long itinerariesId) {
        TrekItinerary itinerary = trekItineraryQueryService.getById(itinerariesId);
        return ServerResponse.<List<RouteWaypointListResponse>>builder()
                .message("Waypoint(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(RouteWaypointListResponse.of(trekWaypointService.getByItineraries(itinerary)))
                .build();
    }

    public ServerResponse<List<RouteWaypointListResponse>> getByStrategy(Long wayPointId) {
        return ServerResponse.<List<RouteWaypointListResponse>>builder()
                .message("Waypoint(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(RouteWaypointListResponse.of(trekWaypointService.getByStrategy(wayPointId)))
                .build();
    }

    public ServerResponse<WayPointResponse> assignToStrategy(Long id, Long strategyId) {
        TrekWaypoint waypoint = trekWaypointService.assignToStrategy(id, strategyId);
        return ServerResponse.<WayPointResponse>builder()
                .message("Waypoint assigned to strategy successfully")
                .status(HttpStatus.OK)
                .data(WayPointResponse.of(waypoint))
                .build();
    }

    public ServerResponse<WayPointResponse> assignToItinerary(Long id, Long itineraryId) {
        TrekWaypoint waypoint = trekWaypointService.assignToItinerary(id, itineraryId);
        return ServerResponse.<WayPointResponse>builder()
                .message("Waypoint assigned to itinerary successfully")
                .status(HttpStatus.OK)
                .data(WayPointResponse.of(waypoint))
                .build();
    }

    public ServerResponse<RouteWaypointDetailResponse> getById(Long id) {
        List<MediaMapping> images = mediaMappingService.getAllImage(id, MediaOwnerType.WAYPOINT);

        return ServerResponse.<RouteWaypointDetailResponse>builder()
                .message("Waypoint detail fetched successfully")
                .status(HttpStatus.OK)
                .data(RouteWaypointDetailResponse.toResponse(trekWaypointService.getById(id), images))
                .build();
    }

}
