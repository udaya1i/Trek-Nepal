package com.trek.core.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

/**
 * @author udaya
 */
@UtilityClass
public class SpecificationUtil {
    public <T> Specification<T> getSpecification(Map<String, String> param, Class<T> clazz) {
        return SpecificationBuilder.builder().filterParams(param).getSpecification(clazz);
    }
}
