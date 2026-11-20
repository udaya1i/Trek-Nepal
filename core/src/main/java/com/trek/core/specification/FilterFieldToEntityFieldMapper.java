package com.trek.core.specification;

import com.trek.core.dto.request.FilterCriteriaRequest;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author udaya
 */
@UtilityClass
public class FilterFieldToEntityFieldMapper {

    public static <T> Map<String, String> map(List<FilterCriteriaRequest> request, Class<T> clazz) {

        Map<String, String> fieldMapper = new HashMap<>();

        request.forEach(field -> {
            if (field.getValue() != null && !field.getValue().isEmpty()) {
                String s = mapField(field.getKey(), clazz);
                if (s != null)
                    fieldMapper.put(mapField(field.getKey(), clazz), field.getValue());
            }
        });
        return fieldMapper;
    }

    private static <T> String mapField(String fieldName, Class<T> clazz) {
        Map<String, String> mappedField = getMappedFieldName(clazz);
        return mappedField.get(fieldName);
    }

    private static Map<String, String> getMappedFieldName(Class<?> clazz) {
        for (FilterFieldRequestToEntityFieldContainer mapper : FilterFieldRequestToEntityFieldContainer.values()) {
            if (mapper.getEntity().equals(clazz))
                return mapper.getField();
        }
        return Map.of();
    }
}
