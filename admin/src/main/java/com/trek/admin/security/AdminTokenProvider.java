package com.trek.admin.security;

import com.trek.core.security.constant.Audience;
import com.trek.core.security.jwt.builder.JwtClaimsBuilder;
import com.trek.core.security.jwt.provider.JwtKeyProvider;
import com.trek.core.security.jwt.provider.JwtMetadata;
import com.trek.core.security.jwt.provider.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * @author udaya
 */
@Component
@AllArgsConstructor
public class AdminTokenProvider implements JwtTokenProvider<AdminPrinciple> {

    private final JwtMetadata jwtMetadata;
    private final JwtClaimsBuilder<AdminPrinciple> jwtClaimsBuilder;
    private final JwtKeyProvider jwtKeyProvider;

    @Override
    public String generateAccessToken(AdminPrinciple detail) {
        return Jwts.builder()
                .claims(jwtClaimsBuilder.accessTokenClaims(detail))
                .subject(detail.getUsername())
                .issuer(jwtMetadata.getIssuer())
                .audience().add(Audience.ADMIN.name())
                .and().issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(jwtMetadata.getAccessTokenExpiration())))
                .signWith(jwtKeyProvider.getAccessKeyProvider())
                .compact();
    }

    @Override
    public String generateRefreshToken(AdminPrinciple detail) {
         return Jwts.builder()
                .claims(jwtClaimsBuilder.refreshTokenClaims(detail))
                .subject(detail.getUsername())
                .issuer(jwtMetadata.getIssuer())
                .audience().add(Audience.ADMIN.name())
                .and().issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(jwtMetadata.getRefreshTokenExpiration())))
                .signWith(jwtKeyProvider.getRefreshKeyProvider())
                .compact();
    }

}
