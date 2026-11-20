package com.trek.core.repository;

import com.trek.core.entity.Comment;
import com.trek.core.entity.CommentReply;
 import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentReplyRepository extends AbstractRepository<CommentReply, Long> {

    @Query("""
    SELECT COUNT(r)
    FROM CommentReply r
    WHERE r.comment.id = :commentId
    AND r.status = 'ACTIVE'
""")
    Long countByCommentId(@Param("commentId") Long commentId);

}
