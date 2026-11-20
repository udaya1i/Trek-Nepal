package com.trek.core.repository;

import com.trek.core.constants.TrekStoryStatus;
import com.trek.core.dto.response.TrekStorySearchable;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.Trekker;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrekStoryRepository extends AbstractRepository<TrekStory, Long> {

    public List<TrekStory> findAllByWriterAndStatus(Trekker writer, TrekStoryStatus status);

    public List<TrekStory> findTop5ByOrderByViewsDesc();

    public long countByStatus(TrekStoryStatus status);

    Optional<TrekStory> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<TrekStory> findBySlugIsNull();

    @Modifying(clearAutomatically = true)
    @Query("update TrekStory s set s.views = coalesce(s.views, 0) + 1 where s.id = :id")
    void incrementViews(@Param("id") Long id);

    @Query("""
    SELECT
        s.id                    AS id,
        s.title                 AS title,
        s.shortDescription      AS shortDescription,
        t.fullName              AS writerFullName,
        t.username              AS writerUsername,
        d.name                  AS destinationName,
        d.region                AS destinationRegion
    FROM TrekStory s
    LEFT JOIN s.writer t
    LEFT JOIN s.destination d
    WHERE s.status <> com.trek.core.constants.TrekStoryStatus.DELETED
""")
    List<TrekStorySearchable> findAllSearchable();

}
