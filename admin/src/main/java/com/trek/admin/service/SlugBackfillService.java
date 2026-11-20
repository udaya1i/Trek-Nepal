package com.trek.admin.service;

import com.trek.core.admin.service.SlugAssignmentService;
import com.trek.core.entity.Destination;
import com.trek.core.entity.Permit;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import com.trek.core.repository.DestinationRepository;
import com.trek.core.repository.RouteWaypointRepository;
import com.trek.core.repository.TrekPermitRepository;
import com.trek.core.repository.TrekStoryRepository;
import com.trek.core.repository.TrekStrategyRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * One-time backfill for legacy rows created before slugs existed. Safe to run
 * repeatedly - only touches rows where slug is still null.
 */
@Service
@AllArgsConstructor
public class SlugBackfillService {

    private final DestinationRepository destinationRepository;
    private final TrekStrategyRepository trekStrategyRepository;
    private final RouteWaypointRepository routeWaypointRepository;
    private final TrekStoryRepository trekStoryRepository;
    private final TrekPermitRepository trekPermitRepository;
    private final SlugAssignmentService slugAssignmentService;

    @Transactional
    public Result backfillAll() {
        int destinations = 0;
        for (Destination destination : destinationRepository.findBySlugIsNull()) {
            destination.setSlug(slugAssignmentService.ensureUniqueSlug(destination.getName(), destinationRepository::existsBySlug));
            destinationRepository.save(destination);
            destinations++;
        }

        int strategies = 0;
        for (TrekStrategy strategy : trekStrategyRepository.findBySlugIsNull()) {
            strategy.setSlug(slugAssignmentService.ensureUniqueSlug(strategy.getName(), trekStrategyRepository::existsBySlug));
            trekStrategyRepository.save(strategy);
            strategies++;
        }

        int waypoints = 0;
        for (TrekWaypoint waypoint : routeWaypointRepository.findBySlugIsNull()) {
            waypoint.setSlug(slugAssignmentService.ensureUniqueSlug(waypoint.getName(), routeWaypointRepository::existsBySlug));
            routeWaypointRepository.save(waypoint);
            waypoints++;
        }

        int stories = 0;
        for (TrekStory story : trekStoryRepository.findBySlugIsNull()) {
            story.setSlug(slugAssignmentService.ensureUniqueSlug(story.getTitle(), trekStoryRepository::existsBySlug));
            trekStoryRepository.save(story);
            stories++;
        }

        int permits = 0;
        for (Permit permit : trekPermitRepository.findBySlugIsNull()) {
            permit.setSlug(slugAssignmentService.ensureUniqueSlug(permit.getName(), trekPermitRepository::existsBySlug));
            trekPermitRepository.save(permit);
            permits++;
        }

        return new Result(destinations, strategies, waypoints, stories, permits);
    }

    @Data
    @AllArgsConstructor
    public static class Result {
        private int destinations;
        private int strategies;
        private int waypoints;
        private int stories;
        private int permits;
    }
}
