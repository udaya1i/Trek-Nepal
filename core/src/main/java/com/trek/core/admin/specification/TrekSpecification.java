package com.trek.core.admin.specification;

import com.trek.core.entity.TrekStrategy;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.specification.FilterFieldToEntityFieldMapper;
import com.trek.core.specification.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

public class TrekSpecification {

    public static Specification<TrekStrategy> searchSpec(FilterRequest request) {
        return SpecificationUtil.getSpecification(
                FilterFieldToEntityFieldMapper
                        .map(request.getFilterCriteriaRequests(), TrekStrategy.class), TrekStrategy.class);
    }

}
