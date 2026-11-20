package com.trek.core.specification;

import java.time.LocalDate;

/**
 * @author udaya
 * @see https://www.moesif.com/blog/technical/api-design/REST-API-Design-Best-Practices-for-Parameters-and-Query-String-Usage/
 */

public class FilterRequestParams {

    private static final String VALUE_OPERATOR_SEPERATOR = ":";
    private final String key;
    private final String value;
    private final SearchOperator operator;

    private FilterRequestParams(String key, String value, SearchOperator operator) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    public static FilterRequestParams of(final String keyWithOperator, String value) {
        String key = keyWithOperator;
        SearchOperator searchOperator;
        if (keyWithOperator.contains(VALUE_OPERATOR_SEPERATOR)) {
            String[] operatorKey = keyWithOperator.split(VALUE_OPERATOR_SEPERATOR);
            searchOperator = SearchOperator.getOperator(operatorKey[0]);
            if (operatorKey.length == 2) {
                key = operatorKey[1];
            }
        } else {
            searchOperator = SearchOperator.LIKE;
        }
        return new FilterRequestParams(key, value, searchOperator);
    }

    public String getKey() {
        return key;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public String getValue() {
        if (SearchOperator.LIKE.equals(this.operator)) {
            return "%" + value + "%";
        }
        return value;
    }

    public LocalDate getValueAsLocalDate() {
        String[] date = value.split("-");
        return LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
    }

}
