package com.trek.core.security.jwt.conf;

import com.trek.core.security.jwt.provider.JwtMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author udaya
 */
@Configuration
public class JwtConfig {

    private final JwtProperties jwtProperties;

    public JwtConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public JwtMetadata jwtMetadata() {
        return new JwtMetadata() {

            @Override
            public String getAccessTokenSecret() {
                return jwtProperties.getAccessTokenSecret();
            }

            @Override
            public String getIssuer() {
                return jwtProperties.getIssuer();
            }

            @Override
            public String getRefreshTokenSecret() {
                return jwtProperties.getRefreshTokenSecret();
            }

            @Override
            public Duration getAccessTokenExpiration() {
                return jwtProperties.getAccessTokenExpiration();
            }

            @Override
            public Duration getRefreshTokenExpiration() {
                return jwtProperties.getRefreshTokenExpiration();
            }
        };
    }
}
