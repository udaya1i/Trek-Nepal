package com.trek.core.trekker.specification;

import com.trek.core.constants.CommentEntityType;
import com.trek.core.constants.TrekStoryStatus;
import com.trek.core.entity.Comment;
import com.trek.core.entity.TrekStory;
import com.trek.core.specification.SpecificationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

@Slf4j
public class CommentSpecification {

    public static Specification<Comment> searchSpec(Map<String, String> map, Long entityId, CommentEntityType entityType) {
        return byEntityIdSpec(entityId)
                .and(notDeletedSpec())
                .and(byEntityTypeSpec(entityType))
                .and(SpecificationUtil.getSpecification(map, Comment.class));
    }

    private static Specification<Comment> byEntityIdSpec(Long entityId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("entityId"), entityId));
    }

    private static Specification<Comment> byEntityTypeSpec(CommentEntityType entityType) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("entityType"), entityType));
    }

    private static Specification<Comment> notDeletedSpec() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), TrekStoryStatus.DELETED));
    }
}
