package com.trek.core.repository;

import com.trek.core.constants.CommentEntityType;
import com.trek.core.entity.Comment;
import com.trek.core.util.EntityCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends AbstractRepository<Comment, Long> {


    @Query("SELECT c.entityId as entityId, COUNT(c) as count FROM Comment c " +
            "WHERE c.entityType = :entityType " +
            "AND c.entityId IN :entityIds " +
            "AND c.status = 'ACTIVE'" +
            "GROUP BY c.entityId")
    List<EntityCount> countByEntityTypeAndEntityIdIn(
            @Param("entityType") CommentEntityType entityType,
            @Param("entityIds") List<Long> entityIds
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Comment c WHERE c.id = :id")
    Optional<Comment> findByIdForUpdate(@Param("id") Long id);

}
