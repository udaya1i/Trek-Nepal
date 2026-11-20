package com.trek.core.admin.specification;

import com.trek.core.constants.DestinationStatus;
import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import com.trek.core.specification.SpecificationUtil;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;
import java.util.Map;

public class DestinationSpecification {

    public static Specification<Destination> searchSpec(String search,
                                                        Map<String, String> filterMap) {
        return
//                publishedSpec().and(
                SpecificationUtil.getSpecification(filterMap, Destination.class).and(search(search));
    }

    public static Specification<Destination> publishedSpec(){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("destinationStatus"), DestinationStatus.PUBLISHED));
    }

    public static Specification<Destination> search(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return null;

            String pattern = "%" + keyword.toLowerCase() + "%";

            Predicate nameLike = cb.like(cb.lower(root.get("name")), pattern);
            Predicate regionLike = cb.like(cb.lower(root.get("region")), pattern);

            Join<?, ?> province = root.join("province", JoinType.LEFT);
            Predicate provinceLike = cb.like(cb.lower(province.get("name")), pattern);

            Subquery<Long> strategySubquery = query.subquery(Long.class);
            Root<TrekStrategy> ts = strategySubquery.from(TrekStrategy.class);
            strategySubquery.select(cb.literal(1L))
                    .where(
                            cb.equal(ts.get("destination"), root),
                            cb.like(cb.lower(ts.get("name")), pattern)
                    );
            Predicate strategyExists = cb.exists(strategySubquery);

            Subquery<Long> waypointSubquery = query.subquery(Long.class);
            Root<TrekWaypoint> tw = waypointSubquery.from(TrekWaypoint.class);
            waypointSubquery.select(cb.literal(1L))
                    .where(
                            cb.equal(tw.get("destination"), root),
                            cb.like(cb.lower(tw.get("name")), pattern)
                    );
            Predicate waypointExists = cb.exists(waypointSubquery);

            return cb.or(
                    nameLike,
                    strategyExists,
                    waypointExists,
                    provinceLike,
                    regionLike
            );
        };
    }
}
