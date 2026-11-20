package com.trek.core.specification;

import com.trek.core.entity.Comment;
import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.TrekStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author udaya
 */
public enum FilterFieldRequestToEntityFieldContainer {

    TREK(TrekStrategy.class, FieldMapper.Trek.getMappedFields()),
    DESTINATION(Destination.class, FieldMapper.Destination.getMappedField()),
    TREK_STORY(TrekStory.class, FieldMapper.TrekStory.getMappedField()),
    COMMENT(Comment.class, FieldMapper.Comment.getMappedField());

    private final Class<?> clazz;
    private final Map<String, String> fieldMapper;

    FilterFieldRequestToEntityFieldContainer(Class<?> clazz, Map<String, String> fieldMapper) {
        this.clazz = clazz;
        this.fieldMapper = fieldMapper;
    }

    public Class<?> getEntity() {
        return clazz;
    }

    public Map<String, String> getField() {
        return fieldMapper;
    }

    static class FieldMapper {

        private static class Trek {
            private static Map<String, String> getMappedFields() {
                Map<String, String> fieldMapper = new HashMap<>();
                fieldMapper.put("name", "name");
                return fieldMapper;
            }
        }

        private static class Destination {
            public static Map<String, String> getMappedField() {
                Map<String, String> filterFieldMap = new HashMap<>();
                filterFieldMap.put("name", "name");
                filterFieldMap.put("province", "eq:province.name");
                filterFieldMap.put("district", "eq:district.name");
                filterFieldMap.put("localLevel", "eq:localLevel.name");
                filterFieldMap.put("destinationType", "eq:destinationType");
                filterFieldMap.put("category", "collect:trailCategory");
                return filterFieldMap;
            }
        }

        private static class Comment {
            public static Map<String, String> getMappedField() {
                Map<String, String> filterFieldMap = new HashMap<>();

                return filterFieldMap;
            }
        }

        private static class TrekStory {
            public static Map<String, String> getMappedField() {
                Map<String, String> filterFieldMap = new HashMap<>();

                return filterFieldMap;
            }
        }
    }

}
