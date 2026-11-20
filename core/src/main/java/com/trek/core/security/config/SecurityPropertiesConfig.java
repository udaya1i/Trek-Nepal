package com.trek.core.security.config;

import com.trek.core.security.jwt.conf.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @author udaya
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityPropertiesConfig {
}
