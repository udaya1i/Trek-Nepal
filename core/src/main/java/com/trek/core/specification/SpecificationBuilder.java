package com.trek.core.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author udaya
 */
public class SpecificationBuilder {

    private final List<FilterRequestParams> filterRequestParams = new ArrayList<>();

    protected SpecificationBuilder() {
    }

    public static SpecificationBuilder builder() {
        return new SpecificationBuilder();
    }

    public SpecificationBuilder filterParams(Map<String, String> parameters) {
        parameters.forEach((key, value) -> {
            FilterRequestParams params = FilterRequestParams.of(key, value);
            if (!"".equals(params.getValue())) {
                filterRequestParams.add(params);
            }
        });
        return this;
    }

    public <T> Specification<T> getSpecification(Class<T> clazz) {
        Specification<T> specification = (root, query, cb) -> null;
        for (FilterRequestParams filterRequest : filterRequestParams) {
            specification = specification.and(specificationBuilder(filterRequest));
        }
        return specification;
    }

    private <T> Specification<T> specificationBuilder(FilterRequestParams filterRequestParams) {
        return ((root, query, criteriaBuilder) -> switch (filterRequestParams.getOperator()) {
            case IN -> SpecificationPredicate.inPredicate(criteriaBuilder, root, filterRequestParams);
            case EQUAL -> SpecificationPredicate.equalPredicate(criteriaBuilder, root, filterRequestParams);
            case GREATER_THEN ->
                    SpecificationPredicate.greaterThanPredicate(criteriaBuilder, root, filterRequestParams);
            case GREATED_THEN_OR_EQUAL ->
                    SpecificationPredicate.greaterThanOrEqualToPredicate(criteriaBuilder, root, filterRequestParams);
            case LESS_THEN -> SpecificationPredicate.lessThanPredicate(criteriaBuilder, root, filterRequestParams);
            case LESS_THEN_OR_EQUAL ->
                    SpecificationPredicate.lessThanOrEqualToPredicate(criteriaBuilder, root, filterRequestParams);
            case BETWEEN -> SpecificationPredicate.betweenPredicate(criteriaBuilder, root, filterRequestParams);
            case COLLECTION -> SpecificationPredicate.collectionEqualPredicate(criteriaBuilder, root, query, filterRequestParams);
            default -> SpecificationPredicate.likePredicate(criteriaBuilder, root, filterRequestParams);
        });
    }
}
