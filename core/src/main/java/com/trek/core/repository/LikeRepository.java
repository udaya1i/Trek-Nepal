package com.trek.core.repository;

import com.trek.core.constants.LikeEntityType;
import com.trek.core.dto.response.LikeResponse;
import com.trek.core.entity.Like;
import com.trek.core.entity.Trekker;
import com.trek.core.util.EntityCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends AbstractRepository<Like, Long> {

    boolean existsByEntityTypeAndEntityIdAndLikedByAndIsLikedTrue(LikeEntityType entityType, Long entityId, Trekker likedBy);

    boolean existsByEntityTypeAndEntityIdAndLikedByAndIsLikedFalse(LikeEntityType entityType, Long entityId, Trekker likedBy);

    Optional<Like> findByEntityTypeAndEntityIdAndLikedBy(LikeEntityType entityType, Long entityId, Trekker likedBy);

    long countByEntityTypeAndEntityIdAndIsLikedTrue(LikeEntityType entityType, Long entityId);

    @Query("""
    select new com.trek.core.dto.response.LikeResponse(
        count(l),
        case when sum(case when l.likedBy.id = :userId and l.isLiked = true then 1 else 0 end) > 0
             then true else false end
    )
    from Like l
    where l.entityType = :entityType
      and l.entityId = :entityId
      and l.isLiked = true
""")
    LikeResponse getLikeResponse(
            @Param("entityType") LikeEntityType entityType,
            @Param("entityId") Long entityId,
            @Param("userId") Long userId
    );

    @Query("""
    select new com.trek.core.dto.response.LikeResponse(
        count(l),
        false
    )
    from Like l
    where l.entityType = :entityType
      and l.entityId = :entityId
      and l.isLiked = true
""")
    LikeResponse getLikeResponse(
            @Param("entityType") LikeEntityType entityType,
            @Param("entityId") Long entityId
    );

    @Query(value = "SELECT l.entity_id AS entityId, COUNT(l.id) AS count " +
            "FROM POST_LIKE l " +
            "WHERE l.entity_type = :entityType " +
            "AND l.entity_id IN :entityIds " +
            "AND l.is_liked = true " +
            "GROUP BY l.entity_id",
            nativeQuery = true)
    List<EntityCount> countLikesByEntityTypeAndEntityIdIn(
            @Param("entityType") String entityType,
            @Param("entityIds") List<Long> entityIds
    );
}
