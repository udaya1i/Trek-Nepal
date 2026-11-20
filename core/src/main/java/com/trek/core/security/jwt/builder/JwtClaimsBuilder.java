package com.trek.core.security.jwt.builder;

import java.util.Map;

/**
 * @param <T>
 * @author udaya
 */
public interface JwtClaimsBuilder<T> {

    Map<String, Object> accessTokenClaims(T detail);
    Map<String, Object> refreshTokenClaims(T detail);
}
