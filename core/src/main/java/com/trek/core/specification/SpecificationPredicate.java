package com.trek.core.specification;

import jakarta.persistence.criteria.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author udaya
 */
@UtilityClass
public class SpecificationPredicate {

    public static Predicate greaterThanPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams filterRequestParams) {

        return criteriaBuilder.greaterThan(
                root.get(filterRequestParams.getKey()), filterRequestParams.getValue());
    }

    public static Predicate lessThanPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams filterRequestParams) {

        return criteriaBuilder.lessThan(
                root.get(filterRequestParams.getKey()), filterRequestParams.getValue());
    }

    public static Predicate greaterThanOrEqualToPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams filterRequestParams) {

        return criteriaBuilder.greaterThanOrEqualTo(
                root.get(filterRequestParams.getKey()), filterRequestParams.getValue());
    }

    public static Predicate lessThanOrEqualToPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams filterRequestParams) {

        return criteriaBuilder.lessThanOrEqualTo(
                root.get(filterRequestParams.getKey()), filterRequestParams.getValue());
    }

    public static Predicate equalPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams filterRequestParams) {

        return criteriaBuilder.equal(
                path(root, filterRequestParams.getKey()), filterRequestParams.getValue());
    }

    public static Predicate likePredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams filterRequestParams) {
        return criteriaBuilder.like(
                (Path<String>) path(root, filterRequestParams.getKey()), filterRequestParams.getValue());
    }

    public static Predicate inPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams searchQueryParam) {

        CriteriaBuilder.In<String> in =
                criteriaBuilder.in((Path<String>) path(root, searchQueryParam.getKey()));

        Arrays.stream(searchQueryParam.getValue().split(","))
                .forEach(search -> in.value(search));
        return in;
    }

    public static Path<?> path(Path<?> root, String fieldName) {
        if (fieldName.contains(".")) {
            String[] nestedFields = fieldName.split("\\.");
            for (String field : nestedFields) {
                root = root.get(field);
            }
        } else {
            root = root.get(fieldName);
        }
        return root;
    }

    public static Predicate betweenPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> root,
            FilterRequestParams filterRequestParams) {

        String[] rangeSeperator = filterRequestParams.getValue().split(",");

        String from = rangeSeperator[0];
        String[] parsed = from.split("-");
        LocalDateTime fromDate =
                LocalDate.of(
                                Integer.valueOf(parsed[0]),
                                Integer.valueOf(parsed[1]),
                                Integer.valueOf(parsed[2]))
                        .atStartOfDay();

        String to = rangeSeperator[1];
        String[] parseTo = to.split("-");
        LocalDateTime toDate =
                LocalDate.of(
                                Integer.valueOf(parseTo[0]),
                                Integer.valueOf(parseTo[1]),
                                Integer.valueOf(parseTo[2]))
                        .plusDays(1)
                        .atStartOfDay();

        return criteriaBuilder.between(root.get(filterRequestParams.getKey()), fromDate, toDate);
    }
    public static Predicate collectionEqualPredicate(
            CriteriaBuilder criteriaBuilder,
            From<?, ?> root,
            CriteriaQuery<?> query,
            FilterRequestParams filterRequestParams) {

        query.distinct(true);

        Join<?, ?> join = root.join(filterRequestParams.getKey(), JoinType.INNER);
        return criteriaBuilder.equal(join, filterRequestParams.getValue());
    }
}
