package com.trek.core.trekker.specification;

import com.trek.core.constants.TrekStoryStatus;
import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.Trekker;
import com.trek.core.specification.SpecificationUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;
import java.util.Map;

@Slf4j
public class TrekStorySpecification {

    public static Specification<TrekStory> searchSpec(String keyword, Map<String, String> map) {
        return notDeletedSpec()
                .and(SpecificationUtil.getSpecification(map, TrekStory.class))
                .and(search(keyword));
    }

    private static Specification<TrekStory> notDeletedSpec() {
        return (root, query, cb) ->
                cb.notEqual(root.get("status"), TrekStoryStatus.DELETED);
    }

    private static Specification<TrekStory> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            String pattern = "%" + keyword.trim().toLowerCase(Locale.ROOT) + "%";

            Predicate titleLike =
                    cb.like(cb.lower(root.get("title")), pattern);

            Join<TrekStory, Destination> destination =
                    root.join("destination", JoinType.LEFT);
            Predicate destinationLike =
                    cb.like(cb.lower(destination.get("name")), pattern);

            Join<TrekStory, Trekker> trekker =
                    root.join("writer", JoinType.LEFT);
            Predicate fullNameLike =
                    cb.like(cb.lower(trekker.get("fullName")), pattern);
            Predicate usernameLike =
                    cb.like(cb.lower(trekker.get("username")), pattern);

            return cb.or(
                    titleLike,
                    destinationLike,
                    fullNameLike,
                    usernameLike
            );
        };
    }
}