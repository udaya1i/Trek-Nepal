package com.trek.admin.controller;

import com.trek.admin.dto.request.TrekWaypointAddRequest;
import com.trek.admin.dto.request.v2.TrekWaypointUpdateRequest;
import com.trek.admin.facade.TrekWayPointFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.RouteWaypointDetailResponse;
import com.trek.core.dto.response.RouteWaypointListResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.WayPointResponse;
import com.trek.core.security.constant.Authorities;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.WAYPOINTS)
public class TrekWayPointController {

    private final TrekWayPointFacade facade;

    @PostMapping
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<WayPointResponse>> add(@RequestBody @Valid TrekWaypointAddRequest request) {
        ServerResponse<WayPointResponse> serverResponse = facade.add(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(AdminPathConstant.UPDATE)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<WayPointResponse>> update(@RequestBody @Valid TrekWaypointUpdateRequest request) {
        ServerResponse<WayPointResponse> serverResponse = facade.update(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID + AdminPathConstant.STRATEGY)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<RouteWaypointListResponse>>> getAllByStrategy(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByStrategy(id));
    }

    @GetMapping(AdminPathConstant.ID + AdminPathConstant.DESTINATION)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<RouteWaypointListResponse>>> getAllByDestination(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByDestination(id));
    }

    @GetMapping(AdminPathConstant.ID + AdminPathConstant.DESTINATION + AdminPathConstant.ALL)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<RouteWaypointListResponse>>> getAllByDestinationComplete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAllByDestination(id));
    }

  @GetMapping(AdminPathConstant.ID + AdminPathConstant.ITINERARY)
  @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<RouteWaypointListResponse>>> findByItineraries(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByItineraries(id));
    }

    @GetMapping(AdminPathConstant.ID)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<RouteWaypointDetailResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @PatchMapping(AdminPathConstant.ID + AdminPathConstant.STRATEGY + AdminPathConstant.STRATEGY_ID)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<WayPointResponse>> assignToStrategy(@PathVariable Long id, @PathVariable Long strategyId) {
        ServerResponse<WayPointResponse> serverResponse = facade.assignToStrategy(id, strategyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PatchMapping(AdminPathConstant.ID + AdminPathConstant.ITINERARY + AdminPathConstant.ITINERARY_ID)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<WayPointResponse>> assignToItinerary(@PathVariable Long id, @PathVariable Long itineraryId) {
        ServerResponse<WayPointResponse> serverResponse = facade.assignToItinerary(id, itineraryId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

}
