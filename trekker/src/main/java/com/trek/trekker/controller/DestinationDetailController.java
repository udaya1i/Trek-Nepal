package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.*;
import com.trek.trekker.dto.response.PublicDestinationDetailResponse;
import com.trek.trekker.dto.response.PublicDestinationResponse;
import com.trek.trekker.facade.PublicTrekDetailFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(PublicPathConstant.BASE_URL)
@AllArgsConstructor
public class DestinationDetailController {

    private final PublicTrekDetailFacade publicTrekDetailFacade;

    @PostMapping(PublicPathConstant.DESTINATIONS)
    public ResponseEntity<PaginatedResponse<PublicDestinationResponse>> getAllDestination(@RequestBody FilterRequest request) {
        PaginatedResponse<PublicDestinationResponse> paginatedResponse = publicTrekDetailFacade.getAllDestination(request);
        return ResponseEntity.status(paginatedResponse.getStatus()).body(paginatedResponse);
    }

    @GetMapping(PublicPathConstant.DESTINATIONS + PublicPathConstant.KEYWORD)
    public ResponseEntity<ServerResponse<List<SearchDestinationProjection.Response>>> searchAllDestination(@PathVariable String keyword) {
        ServerResponse<List<SearchDestinationProjection.Response>> serverResponse = publicTrekDetailFacade.searchDestination(keyword);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.DESTINATIONS + PublicPathConstant.SLUG + PublicPathConstant.DETAIL)
    public ResponseEntity<ServerResponse<PublicDestinationDetailResponse>> getDestinationDetail(@PathVariable String slug) {
        ServerResponse<PublicDestinationDetailResponse> response = publicTrekDetailFacade.getDestinationDetail(slug);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.DESTINATIONS + PublicPathConstant.ID + PublicPathConstant.STRATEGIES)
    public ResponseEntity<ServerResponse<List<TrekStrategyResponse>>> getAllStrategy(@PathVariable Long id) {
        ServerResponse<List<TrekStrategyResponse>> response = publicTrekDetailFacade.getAllStrategy(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.STRATEGIES + PublicPathConstant.SLUG + PublicPathConstant.DETAIL)
    public ResponseEntity<ServerResponse<TrekStrategyDetailResponse>> getStrategyDetail(@PathVariable String slug) {
        ServerResponse<TrekStrategyDetailResponse> response = publicTrekDetailFacade.getStrategyDetail(slug);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.STRATEGIES + PublicPathConstant.ID + PublicPathConstant.ITINERARIES)
    public ResponseEntity<ServerResponse<List<TrekItinerariesResponse>>> getAllItinerary(@PathVariable Long id) {
        ServerResponse<List<TrekItinerariesResponse>> response = publicTrekDetailFacade.getAllItinerary(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.ITINERARIES + PublicPathConstant.ID + PublicPathConstant.DETAIL)
    public ResponseEntity<ServerResponse<TrekItineraryDetailResponse>> getItineraryDetail(@PathVariable Long id) {
        ServerResponse<TrekItineraryDetailResponse> response = publicTrekDetailFacade.getItineraryDetail(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.STRATEGIES + PublicPathConstant.ID + PublicPathConstant.PERMITS)
    public ResponseEntity<ServerResponse<List<DestinationPermitResponse>>> getAllPermit(@PathVariable Long id) {
        ServerResponse<List<DestinationPermitResponse>> response = publicTrekDetailFacade.getAllPermit(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.STRATEGIES + PublicPathConstant.ID + PublicPathConstant.PACKAGINGS)
    public ResponseEntity<ServerResponse<List<TrekPackagingResponse>>> getAllPacking(@PathVariable Long id) {
        ServerResponse<List<TrekPackagingResponse>> response = publicTrekDetailFacade.getAllPacking(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.STRATEGIES + PublicPathConstant.ID + PublicPathConstant.WAYPOINTS)
    public ResponseEntity<ServerResponse<List<WayPointResponse>>> getAllWaypoints(@PathVariable Long id) {
        ServerResponse<List<WayPointResponse>> response = publicTrekDetailFacade.getAllWaypoints(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @GetMapping(PublicPathConstant.WAYPOINTS + PublicPathConstant.SLUG + PublicPathConstant.DETAIL)
    public ResponseEntity<ServerResponse<WayPointDetailResponse>> getWaypointDetail(@PathVariable String slug) {
        ServerResponse<WayPointDetailResponse> response = publicTrekDetailFacade.getWaypointDetail(slug);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.ITINERARIES + PublicPathConstant.ID + PublicPathConstant.WAYPOINTS)
    public ResponseEntity<ServerResponse<List<WayPointResponse>>> getWaypointByItinerary(@PathVariable Long id) {
        ServerResponse<List<WayPointResponse>> response = publicTrekDetailFacade.getAllWaypointsByItinerary(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping(PublicPathConstant.DESTINATIONS + PublicPathConstant.ID + PublicPathConstant.IMAGE)
    public ResponseEntity<ServerResponse<List<ImageResponse>>> getAllImage(@PathVariable Long id) {
        ServerResponse<List<ImageResponse>> response = publicTrekDetailFacade.getAllImage(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping(PublicPathConstant.DESTINATIONS + PublicPathConstant.SLUG + PublicPathConstant.VIEW)
    public ResponseEntity<ServerResponse<Void>> recordDestinationView(@PathVariable String slug) {
        ServerResponse<Void> response = publicTrekDetailFacade.recordDestinationView(slug);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping(PublicPathConstant.STRATEGIES + PublicPathConstant.SLUG + PublicPathConstant.VIEW)
    public ResponseEntity<ServerResponse<Void>> recordStrategyView(@PathVariable String slug) {
        ServerResponse<Void> response = publicTrekDetailFacade.recordStrategyView(slug);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping(PublicPathConstant.WAYPOINTS + PublicPathConstant.SLUG + PublicPathConstant.VIEW)
    public ResponseEntity<ServerResponse<Void>> recordWaypointView(@PathVariable String slug) {
        ServerResponse<Void> response = publicTrekDetailFacade.recordWaypointView(slug);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}