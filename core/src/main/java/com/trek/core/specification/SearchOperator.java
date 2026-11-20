package com.trek.core.specification;

/**
 * @author udaya
 */
public enum SearchOperator {

    EQUAL("eq"),
    LIKE("lk"),
    GREATER_THEN("gt"),
    LESS_THEN("lt"),
    GREATED_THEN_OR_EQUAL("gte"),
    LESS_THEN_OR_EQUAL("lte"),
    IN("in"),
    BETWEEN("btn"),
    IS_NULL("isNull"),
    COLLECTION("collect"),
    NOT_NULL("notNull");

    private final String operator;


    SearchOperator(String name) {
        this.operator = name;
    }

    public static SearchOperator getOperator(String key) {
        for (SearchOperator so : SearchOperator.values()) {
            if (so.operator.equals(key)) {
                return so;
            }
        }
        throw new RuntimeException("Invalid filter operator");
    }
}
