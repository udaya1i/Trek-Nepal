package com.trek.core.trekker.service;

import com.trek.core.constants.SluggableType;
import com.trek.core.entity.Destination;
import com.trek.core.entity.Permit;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import com.trek.core.repository.DestinationRepository;
import com.trek.core.repository.SlugHistoryRepository;
import com.trek.core.repository.TrekPermitRepository;
import com.trek.core.repository.TrekStoryRepository;
import com.trek.core.repository.TrekStrategyRepository;
import com.trek.core.repository.WayPointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Resolves a stale (renamed) slug to the owning entity's current slug, so the
 * frontend can 301 old SEO URLs instead of serving a 404.
 */
@Service
@AllArgsConstructor
public class SlugRedirectQueryService {

    private final SlugHistoryRepository slugHistoryRepository;
    private final DestinationRepository destinationRepository;
    private final TrekStrategyRepository trekStrategyRepository;
    private final WayPointRepository wayPointRepository;
    private final TrekStoryRepository trekStoryRepository;
    private final TrekPermitRepository trekPermitRepository;

    @Transactional(readOnly = true)
    public Optional<String> resolveCurrentSlug(SluggableType type, String oldSlug) {
        return slugHistoryRepository.findByOwnerTypeAndOldSlug(type, oldSlug)
                .flatMap(history -> currentSlugFor(type, history.getOwnerId()));
    }

    private Optional<String> currentSlugFor(SluggableType type, Long ownerId) {
        return switch (type) {
            case DESTINATION -> destinationRepository.findById(ownerId).map(Destination::getSlug);
            case STRATEGY -> trekStrategyRepository.findById(ownerId).map(TrekStrategy::getSlug);
            case WAYPOINT -> wayPointRepository.findById(ownerId).map(TrekWaypoint::getSlug);
            case STORY -> trekStoryRepository.findById(ownerId).map(TrekStory::getSlug);
            case PERMIT -> trekPermitRepository.findById(ownerId).map(Permit::getSlug);
        };
    }
}
