package com.trek.trekker.security;

import com.trek.core.security.jwt.builder.JwtClaimsBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author udaya
 */
@Component("trekker")
public class TrekkerClaimsBuilder implements JwtClaimsBuilder<TrekkerPrinciple> {

    @Override
    public Map<String, Object> accessTokenClaims(TrekkerPrinciple detail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "ACCESS");
        claims.put("roles", detail.getAuthorities());
        return claims;
    }

    @Override
    public Map<String, Object> refreshTokenClaims(TrekkerPrinciple detail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "REFRESH");
        claims.put("jti", UUID.randomUUID().toString());
        return claims;
    }
}
