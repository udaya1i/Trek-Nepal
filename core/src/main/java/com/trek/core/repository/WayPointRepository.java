package com.trek.core.repository;

import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.entity.TrekWaypoint;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WayPointRepository extends AbstractRepository<TrekWaypoint, Long> {

    Optional<TrekWaypoint> findBySlug(String slug);

    boolean existsBySlug(String slug);

    @Modifying(clearAutomatically = true)
    @Query("update TrekWaypoint w set w.views = coalesce(w.views, 0) + 1 where w.id = :id")
    void incrementViews(@Param("id") Long id);
}