package com.trek.admin.security;

import com.trek.core.security.constant.Audience;
import com.trek.core.security.constant.ValidationResult;
import com.trek.core.security.jwt.provider.JwtKeyProvider;
import com.trek.core.security.jwt.provider.JwtMetadata;
import com.trek.core.security.jwt.validator.TokenValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * @author udaya
 */
@Slf4j
@Component
@AllArgsConstructor
public class AdminAccessTokenValidator implements TokenValidator {

    private final JwtKeyProvider provider;
    private final JwtMetadata jwtMetadata;

    @Override
    public ValidationResult validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(provider.getAccessKeyProvider())
                    .requireIssuer(jwtMetadata.getIssuer())
                    .requireAudience(Audience.ADMIN.name())
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

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return expireExpiration(token).before(new Date());
    }

    public Date expireExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(provider.getAccessKeyProvider())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
