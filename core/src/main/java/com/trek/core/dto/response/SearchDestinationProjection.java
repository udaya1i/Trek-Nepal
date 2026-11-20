package com.trek.core.dto.response;

import com.trek.core.constants.SearchEntityType;

import java.io.Serializable;

/**
 * @author udaya
 */
public interface SearchDestinationProjection {

    SearchEntityType getType();

    Long getId();

    String getName();

    record Response(Long destinationId, String destinationName, SearchEntityType type, Long matchedEntityId,
                    String matchedName) implements Serializable {

        public static Response from(DestinationSearchable p, SearchEntityType type) {
            Long matchedId = switch (type) {
                case DESTINATION -> p.getId();
                case REGION -> p.getId();
                case STRATEGY -> p.getStrategyId();
                case WAYPOINT -> p.getWaypointId();
            };
            String matchedName = switch (type) {
                case DESTINATION -> p.getName();
                case REGION -> p.getRegion();
                case STRATEGY -> p.getStrategyName();
                case WAYPOINT -> p.getWaypointName();
            };
            return new Response(p.getId(), p.getName(), type, matchedId, matchedName);
        }

    }
}