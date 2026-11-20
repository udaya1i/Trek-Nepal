package com.trek.core.repository;

import com.trek.core.constants.DestinationStatus;
import com.trek.core.dto.response.DestinationCategoryCountProjection;
import com.trek.core.dto.response.DestinationSearchable;
import com.trek.core.dto.response.SearchDestinationProjection;
import com.trek.core.entity.Destination;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DestinationRepository extends AbstractRepository<Destination, Long> {

    Optional<Destination> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Destination> findBySlugIsNull();

    @Modifying(clearAutomatically = true)
    @Query("update Destination d set d.views = coalesce(d.views, 0) + 1 where d.id = :id")
    void incrementViews(@Param("id") Long id);

    @EntityGraph(attributePaths = {"province", "district", "localLevel", "highlights"})
    Page<Destination> findAll(@NonNull Specification<Destination> spec, @NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"province", "district", "localLevel", "highlights"})
    List<Destination> findTop5ByOrderByViewsDesc();

    long countByDestinationStatus(DestinationStatus destinationStatus);

    @Query("""
                SELECT tc AS categoryName,
                            COUNT(d) AS totalNumber
                                        FROM Destination d join
                                                     d.trailCategory tc group by tc order by COUNT (d) desc
            """)
    List<DestinationCategoryCountProjection> countDestinationByTrailCategory();

    @Query("""
        select tw.id
        from TrekWaypoint tw
        where tw.destination.id = :destinationId
        """)
    List<Long> findWaypointIdsByDestinationId(Long destinationId);


    @Query("""
        SELECT
            d.id            AS id,
            d.name          AS name,
            d.region        AS region,
            p.name          AS provinceName,
            ts.id           AS strategyId,
            ts.name         AS strategyName,
            tw.id           AS waypointId,
            tw.name         AS waypointName
        FROM Destination d
        LEFT JOIN d.province p
        LEFT JOIN d.trekStrategy ts
        LEFT JOIN d.waypoints tw
        """)
    List<DestinationSearchable> findAllWithNames();
}
