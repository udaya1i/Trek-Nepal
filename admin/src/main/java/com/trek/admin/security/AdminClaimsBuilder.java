package com.trek.admin.security;

import com.trek.core.entity.Authority;
import com.trek.core.security.jwt.builder.JwtClaimsBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author udaya
 */
@Component("admin")
public class AdminClaimsBuilder implements JwtClaimsBuilder<AdminPrinciple> {

    @Override
    public Map<String, Object> accessTokenClaims(AdminPrinciple detail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "ACCESS");
        claims.put("roles", detail.getAllAuthority().stream().map(Authority::getCode).toList());
        return claims;
    }

    @Override
    public Map<String, Object> refreshTokenClaims(AdminPrinciple detail) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "REFRESH");
        claims.put("jti", UUID.randomUUID().toString());
        return claims;
    }
}
