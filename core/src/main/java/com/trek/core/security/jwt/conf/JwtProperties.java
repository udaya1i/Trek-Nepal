package com.trek.core.security.jwt.conf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * @author udaya
 */
@Getter
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotBlank
    private final String accessTokenSecret;

    @NotBlank
    private final String refreshTokenSecret;

    @NotNull
    private final Duration accessTokenExpiration;

    @NotNull
    private final Duration refreshTokenExpiration;

    @NotBlank
    private final String issuer;

    @ConstructorBinding
    public JwtProperties(
            String accessTokenSecret,
            String refreshTokenSecret,
            Duration accessTokenExpiration,
            Duration refreshTokenExpiration,
            String issuer) {
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.issuer = issuer;
    }

}