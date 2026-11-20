package com.trek.core.security.jwt.provider.impl;

import com.trek.core.security.jwt.provider.JwtKeyProvider;
import com.trek.core.security.jwt.provider.JwtMetadata;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * @author udaya
 */
@Component
@AllArgsConstructor
public class JwtAccessRefreshKeyProvider implements JwtKeyProvider {
    private final JwtMetadata jwtMetadata;

    @Override
    public SecretKey getRefreshKeyProvider() {
        byte[] key = jwtMetadata.getRefreshTokenSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public SecretKey getAccessKeyProvider() {
        byte[] key = jwtMetadata.getAccessTokenSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(key);
    }
}
