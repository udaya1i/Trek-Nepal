package com.trek.core.repository;

import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekStrategy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrekStrategyRepository extends AbstractRepository<TrekStrategy, Long> {

    List<TrekStrategy> findByDestination(Destination destination);

    Optional<TrekStrategy> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<TrekStrategy> findBySlugIsNull();

    @Modifying(clearAutomatically = true)
    @Query("update TrekStrategy s set s.views = coalesce(s.views, 0) + 1 where s.id = :id")
    void incrementViews(@Param("id") Long id);
}
