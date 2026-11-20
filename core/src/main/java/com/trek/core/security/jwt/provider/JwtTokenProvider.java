package com.trek.core.security.jwt.provider;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @param <T>
 * @author udaya
 */
public interface JwtTokenProvider<T extends UserDetails> {

    public String generateAccessToken(T detail);

    public String generateRefreshToken(T detail);
}
