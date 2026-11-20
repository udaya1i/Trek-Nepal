package com.trek.core.security.jwt.provider;

import java.time.Duration;

/**
 * @author udaya
 */
public interface JwtMetadata {

    String getIssuer();

    String getAccessTokenSecret();

    String getRefreshTokenSecret();

    Duration getAccessTokenExpiration();

    Duration getRefreshTokenExpiration();
}
