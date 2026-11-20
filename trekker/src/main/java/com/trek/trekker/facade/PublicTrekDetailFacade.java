package com.trek.trekker.facade;

import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.*;
import com.trek.core.entity.*;
import com.trek.core.trekker.service.PublicDestinationDetailQueryService;
import com.trek.trekker.dto.response.PublicDestinationDetailResponse;
import com.trek.trekker.dto.response.PublicDestinationResponse;
import com.trek.trekker.service.DestinationReviewService;
import com.trek.trekker.service.JaroSearchFallbackService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicTrekDetailFacade {

    private final PublicDestinationDetailQueryService publicDestinationDetailQueryService;
    private final MediaMappingService mediaMappingService;
    private final DestinationReviewService destinationReviewService;
    private final JaroSearchFallbackService searchDestination;

    public ServerResponse<PublicDestinationDetailResponse> getDestinationDetail(String slug) {
        Destination destination = publicDestinationDetailQueryService.getDestinationDetail(slug);
        List<MediaMapping> images = mediaMappingService.getAllImage(List.of(destination.getId()), MediaOwnerType.DESTINATION);
        return ServerResponse.<PublicDestinationDetailResponse>builder()
                .message("Destination detail fetched")
                .status(HttpStatus.OK)
                .data(PublicDestinationDetailResponse.of(destination, images, destinationReviewService.getStatus(destination.getId())))
                .build();
    }

    public ServerResponse<List<SearchDestinationProjection.Response>> searchDestination(String keyword) {
        List<SearchDestinationProjection.Response> responses = searchDestination.searchDestination(keyword);
        return ServerResponse.<List<SearchDestinationProjection.Response>>builder()
                .data(responses)
                .message("Destination(s) fetched successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public PaginatedResponse<PublicDestinationResponse> getAllDestination(FilterRequest request) {

        Page<Destination> destinations = publicDestinationDetailQueryService.getAllDestination(request);

        List<Long> ids = destinations.getContent().stream().map(BaseEntity::getId).toList();
        Map<Long, List<MediaMapping>> collect = mediaMappingService.getAllImage(ids, MediaOwnerType.DESTINATION).stream()
                .collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        return PaginatedResponse.data(destinations.map(destination -> PublicDestinationResponse.of(destination,
                        collect.getOrDefault(destination.getId(), List.of()),
                        destinationReviewService.getStatus(destination.getId()))))
                .message("Destination(s) fetched successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<TrekStrategyResponse>> getAllStrategy(Long destinationId) {
        List<TrekStrategy> allStrategy = publicDestinationDetailQueryService.getAllStrategy(destinationId);
        return ServerResponse.<List<TrekStrategyResponse>>builder()
                .message("Strategy(s) fetched")
                .data(com.trek.core.dto.response.TrekStrategyResponse.from(allStrategy))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<TrekStrategyDetailResponse> getStrategyDetail(String slug) {
        TrekStrategy strategyDetail = publicDestinationDetailQueryService.getStrategyDetail(slug);
        return ServerResponse.<TrekStrategyDetailResponse>builder()
                .message("Strategy detail fetched")
                .data(com.trek.core.dto.response.TrekStrategyDetailResponse.toResponse(strategyDetail))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<TrekItinerariesResponse>> getAllItinerary(Long strategyId) {
        List<TrekItinerary> allItinerary = publicDestinationDetailQueryService.getAllItinerary(strategyId);
        return ServerResponse.<List<TrekItinerariesResponse>>builder()
                .message("Itinerary(s) fetched")
                .data(com.trek.core.dto.response.TrekItinerariesResponse.of(allItinerary))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<TrekItineraryDetailResponse> getItineraryDetail(Long itineraryId) {
        TrekItinerary itineraryDetail = publicDestinationDetailQueryService.getItineraryDetail(itineraryId);
        return ServerResponse.<TrekItineraryDetailResponse>builder()
                .message("Itinerary detail fetched")
                .data(com.trek.core.dto.response.TrekItineraryDetailResponse.of(itineraryDetail))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<WayPointResponse>> getAllWaypointsByItinerary(Long itineraryId) {

        List<TrekWaypoint> allWaypoints = publicDestinationDetailQueryService.getWaypointsByItinerary(itineraryId);

        Map<Long, List<MediaMapping>> mediaMap = mediaMappingService.getAllImage(allWaypoints.stream().map(BaseEntity::getId).toList(), MediaOwnerType.WAYPOINT)
                .stream().collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        return ServerResponse.<List<WayPointResponse>>builder()
                .message("Waypoint(s) fetched")
                .data(allWaypoints.stream().map(w -> {
                    List<MediaMapping> mediaMappings = mediaMap.getOrDefault(w.getId(), List.of());
                    return WayPointResponse.of(w, mediaMappings);
                }).toList())
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<WayPointResponse>> getAllWaypoints(Long strategyId) {
        List<TrekWaypoint> allWaypoints = publicDestinationDetailQueryService.getAllWaypoints(strategyId);

        Map<Long, List<MediaMapping>> mediaMap = mediaMappingService.getAllImage(allWaypoints.stream().map(BaseEntity::getId).toList(), MediaOwnerType.WAYPOINT)
                .stream().collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        return ServerResponse.<List<WayPointResponse>>builder()
                .message("Waypoint(s) fetched")
                .data(allWaypoints.stream().map(w -> {
                    List<MediaMapping> mediaMappings = mediaMap.getOrDefault(w.getId(), List.of());
                    return WayPointResponse.of(w, mediaMappings);
                }).toList())
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<WayPointDetailResponse> getWaypointDetail(String slug) {
        TrekWaypoint waypoint = publicDestinationDetailQueryService.getWaypointDetail(slug);
        List<MediaMapping> images = mediaMappingService.getAllImage(waypoint.getId(), MediaOwnerType.WAYPOINT);
        return ServerResponse.<WayPointDetailResponse>builder()
                .message("Waypoint detail fetched")
                .data(com.trek.core.dto.response.WayPointDetailResponse.of(waypoint, images))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<DestinationPermitResponse>> getAllPermit(Long strategyId) {
        List<Permit> allPermit = publicDestinationDetailQueryService.getAllPermit(strategyId);
        return ServerResponse.<List<DestinationPermitResponse>>builder()
                .message("Permit(s) fetched")
                .data(com.trek.core.dto.response.DestinationPermitResponse.of(allPermit))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<TrekPackagingResponse>> getAllPacking(Long strategyId) {
        List<TrekPacking> allPacking = publicDestinationDetailQueryService.getAllPacking(strategyId);
        return ServerResponse.<List<TrekPackagingResponse>>builder()
                .message("Packing(s) fetched")
                .data(allPacking.stream().map(items ->
                        new TrekPackagingResponse(
                                items.getName(),
                                items.getItems())).toList())
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<ImageResponse>> getAllImage(Long destinationId) {
        List<MediaMapping> images = publicDestinationDetailQueryService.getImages(
                publicDestinationDetailQueryService.getDestinationDetail(destinationId));
        return ServerResponse.<List<ImageResponse>>builder()
                .message("Images fetched")
                .data(ImageResponse.of(images))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<Void> recordDestinationView(String slug) {
        publicDestinationDetailQueryService.incrementDestinationViews(slug);
        return ServerResponse.<Void>builder().status(HttpStatus.OK).message("View recorded successfully").build();
    }

    public ServerResponse<Void> recordStrategyView(String slug) {
        publicDestinationDetailQueryService.incrementStrategyViews(slug);
        return ServerResponse.<Void>builder().status(HttpStatus.OK).message("View recorded successfully").build();
    }

    public ServerResponse<Void> recordWaypointView(String slug) {
        publicDestinationDetailQueryService.incrementWaypointViews(slug);
        return ServerResponse.<Void>builder().status(HttpStatus.OK).message("View recorded successfully").build();
    }

}
