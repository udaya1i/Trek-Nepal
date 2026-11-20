package com.trek.core.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * @author udaya
 */

@UtilityClass
public class UpdateUtil {

    public static <T> T getOrDefault(T oldValue, T newValue) {
        return Objects.nonNull(newValue) ? newValue : oldValue;
    }

}
