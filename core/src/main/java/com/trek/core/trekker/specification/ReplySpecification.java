package com.trek.core.trekker.specification;

import com.trek.core.constants.TrekStoryStatus;
import com.trek.core.entity.Comment;
import com.trek.core.entity.CommentReply;
import com.trek.core.specification.SpecificationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

@Slf4j
public class ReplySpecification {

    public static Specification<CommentReply> searchSpec(Map<String, String> map, Long entityId) {
        return byCommentIdSpec(entityId)
                .and(notDeletedSpec())
                .and(SpecificationUtil.getSpecification(map, CommentReply.class));
    }

    private static Specification<CommentReply> byCommentIdSpec(Long commentId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("comment").get("id"), commentId));
    }

    private static Specification<CommentReply> notDeletedSpec() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), TrekStoryStatus.DELETED));
    }

}
