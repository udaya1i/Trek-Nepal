package com.trek.core.trekker.service;

import com.trek.core.admin.service.*;
import com.trek.core.admin.specification.DestinationSpecification;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.DestinationSearchable;
import com.trek.core.dto.response.SearchDestinationProjection;
import com.trek.core.entity.*;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.DestinationRepository;
import com.trek.core.repository.TrekPackagingRepository;
import com.trek.core.repository.TrekStrategyRepository;
import com.trek.core.repository.WayPointRepository;
import com.trek.core.specification.FilterFieldToEntityFieldMapper;
import com.trek.core.util.PaginationUtil;
import com.trek.core.util.SlugUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PublicDestinationDetailQueryService {

    public final WaypointQueryService waypointQueryService;
    private final DestinationQueryService destinationQueryService;
    private final TrekStrategyQueryService strategyQueryService;
    private final TrekItineraryQueryService trekItineraryQueryService;
    private final TrekPermitQueryService permitQueryService;
    private final TrekPackagingRepository trekPackagingService;
    private final DestinationRepository destinationRepository;
    private final TrekStrategyRepository trekStrategyRepository;
    private final WayPointRepository wayPointRepository;
    private final MediaMappingService mediaMappingService;

    @Transactional(readOnly = true)
    public Destination getDestinationDetail(Long destinationId) {
        return destinationQueryService.getById(destinationId);
    }

    /**
     * Slug-based lookup used by the public detail endpoint. Goes straight to the
     * repository (not destinationQueryService.getById) so it doesn't inherit the
     * view-count-as-a-side-effect bug there; falls back to numeric id for legacy links.
     */
    @Transactional(readOnly = true)
    public Destination getDestinationDetail(String slug) {
        return destinationRepository.findBySlug(slug)
                .or(() -> SlugUtil.isNumeric(slug) ? destinationRepository.findById(Long.parseLong(slug)) : Optional.empty())
                .orElseThrow(() -> new RequestValidationException("Invalid destination"));
    }

    @Transactional(readOnly = true)
    public Page<Destination> getAllDestination(FilterRequest request) {
        Specification<Destination> destinationSpecification = DestinationSpecification.searchSpec(request.getSearch(),  FilterFieldToEntityFieldMapper
                .map(request.getFilterCriteriaRequests(), Destination.class));
        return destinationRepository.findAll(destinationSpecification, PaginationUtil.of(request.getPaginationRequest()));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "destinations")
    public List<DestinationSearchable> getAllNames() {
        return destinationRepository.findAllWithNames();
    }

    /**
     * This cache has no write-path eviction (destinations are created/updated from the
     * separate `admin` deployment, so an in-process @CacheEvict there wouldn't reach this
     * JVM's cache). Periodically clearing it bounds staleness instead of caching forever.
     */
    @CacheEvict(cacheNames = "destinations", allEntries = true)
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void evictDestinationNamesCache() {
    }

    @Transactional(readOnly = true)
    public List<TrekStrategy> getAllStrategy(Long destinationId) {
        return strategyQueryService.getByDestination(destinationId);
    }

    @Transactional(readOnly = true)
    public TrekStrategy getStrategyDetail(String slug) {
        return trekStrategyRepository.findBySlug(slug)
                .or(() -> SlugUtil.isNumeric(slug) ? trekStrategyRepository.findById(Long.parseLong(slug)) : Optional.empty())
                .orElseThrow(() -> new RequestValidationException("Invalid strategy"));
    }

    @Transactional(readOnly = true)
    public List<TrekItinerary> getAllItinerary(Long strategyId) {
        return trekItineraryQueryService.getByStrategy(strategyQueryService.getById(strategyId));
    }

    @Transactional(readOnly = true)
    public TrekItinerary getItineraryDetail(Long itineraryId) {
        return trekItineraryQueryService.getById(itineraryId);
    }

    @Transactional(readOnly = true)
    public List<Permit> getAllPermit(Long strategyId) {
        return permitQueryService.getAllByStrategy(strategyId);
    }

    @Transactional(readOnly = true)
    public List<TrekPacking> getAllPacking(Long strategyId) {
        return trekPackagingService.getByTrekStrategy(strategyQueryService.getById(strategyId));
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> getAllWaypoints(Long strategyId) {
        return waypointQueryService.getByStrategy(strategyQueryService.getById(strategyId));
    }

    @Transactional(readOnly = true)
    public TrekWaypoint getWaypointDetail(String slug) {
        return wayPointRepository.findBySlug(slug)
                .or(() -> SlugUtil.isNumeric(slug) ? wayPointRepository.findById(Long.parseLong(slug)) : Optional.empty())
                .orElseThrow(() -> new RequestValidationException("Invalid waypoint"));
    }

    @Transactional(readOnly = true)
    public List<TrekWaypoint> getWaypointsByItinerary(Long itineraryId) {
        TrekItinerary itinerary = trekItineraryQueryService.getById(itineraryId);
        return waypointQueryService.getByItinerary(itinerary);
    }

    public List<MediaMapping> getImages(Destination destination) {
        List<Long> waypointIds = destinationRepository.findWaypointIdsByDestinationId(destination.getId());
        return Stream.concat(
                mediaMappingService.getAllImage(waypointIds, MediaOwnerType.WAYPOINT).stream(),
                mediaMappingService.getAllImage(destination.getId(), MediaOwnerType.DESTINATION).stream()
        ).toList();
    }

    /**
     * Explicit, bot-agnostic view recording - called by the frontend once per page
     * view, not as a side effect of any GET. Atomic bulk update, no read-modify-write race.
     */
    @Transactional
    public void incrementDestinationViews(String slug) {
        destinationRepository.incrementViews(getDestinationDetail(slug).getId());
    }

    @Transactional
    public void incrementStrategyViews(String slug) {
        trekStrategyRepository.incrementViews(getStrategyDetail(slug).getId());
    }

    @Transactional
    public void incrementWaypointViews(String slug) {
        wayPointRepository.incrementViews(getWaypointDetail(slug).getId());
    }

}
