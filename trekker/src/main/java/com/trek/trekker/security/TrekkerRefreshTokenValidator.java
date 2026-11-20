package com.trek.trekker.security;

import com.trek.core.security.constant.Audience;
import com.trek.core.security.constant.ValidationResult;
import com.trek.core.security.jwt.provider.JwtKeyProvider;
import com.trek.core.security.jwt.provider.JwtMetadata;
import com.trek.core.security.jwt.validator.TokenValidator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TrekkerRefreshTokenValidator implements TokenValidator {

    private final JwtKeyProvider provider;
    private final JwtMetadata jwtMetadata;


    @Override
    public ValidationResult validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(provider.getRefreshKeyProvider())
                    .requireIssuer(jwtMetadata.getIssuer())
                    .requireAudience(Audience.TREKKER.name())
                    .build()
                    .parseSignedClaims(token);
            return ValidationResult.VALID;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return ValidationResult.INVALID;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            return ValidationResult.EXPIRED;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            return ValidationResult.INVALID;
        } catch (Exception e) {
            log.error("Invalid Token: {}", e.getMessage());
            return ValidationResult.INVALID;
        }
    }
}
