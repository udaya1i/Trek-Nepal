package com.trek.core.security.jwt.provider;

import javax.crypto.SecretKey;

/**
 * @author udaya
 */
public interface JwtKeyProvider {
    SecretKey getRefreshKeyProvider();
    SecretKey getAccessKeyProvider();
}
