package com.trek.trekker.security;

import com.trek.core.security.constant.Audience;
import com.trek.core.security.jwt.builder.JwtClaimsBuilder;
import com.trek.core.security.jwt.provider.JwtKeyProvider;
import com.trek.core.security.jwt.provider.JwtMetadata;
import com.trek.core.security.jwt.provider.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * @author udaya
 */
@Service
public class TrekkerTokenProvider implements JwtTokenProvider<TrekkerPrinciple> {

    private final JwtMetadata jwtMetadata;
    private final JwtClaimsBuilder<TrekkerPrinciple> jwtClaimsBuilder;
    private final JwtKeyProvider jwtKeyProvider;

    public TrekkerTokenProvider(JwtMetadata jwtMetadata,
                                @Qualifier("trekker") JwtClaimsBuilder<TrekkerPrinciple> jwtClaimsBuilder,
                                JwtKeyProvider jwtKeyProvider) {
        this.jwtMetadata = jwtMetadata;
        this.jwtClaimsBuilder = jwtClaimsBuilder;
        this.jwtKeyProvider = jwtKeyProvider;
    }

    @Override
    public String generateAccessToken(TrekkerPrinciple detail) {
        return Jwts.builder()
                .claims(jwtClaimsBuilder.accessTokenClaims(detail))
                .subject(detail.getUsername())
                .issuer(jwtMetadata.getIssuer())
                .audience().add(Audience.TREKKER.name())
                .and().issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(jwtMetadata.getAccessTokenExpiration())))
                .signWith(jwtKeyProvider.getAccessKeyProvider())
                .compact();
    }

    @Override
    public String generateRefreshToken(TrekkerPrinciple detail) {
        return Jwts.builder()
                .claims(jwtClaimsBuilder.refreshTokenClaims(detail))
                .subject(detail.getUsername())
                .issuer(jwtMetadata.getIssuer())
                .audience().add(Audience.TREKKER.name())
                .and().issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(jwtMetadata.getRefreshTokenExpiration())))
                .signWith(jwtKeyProvider.getRefreshKeyProvider())
                .compact();
    }
}
