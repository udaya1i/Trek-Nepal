package com.trek.trekker;

import com.trek.core.config.db.DataSourceProperties;
import com.trek.core.security.jwt.conf.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.trek.trekker", "com.trek.core"})
@EnableConfigurationProperties({DataSourceProperties.class, JwtProperties.class})
@EntityScan(basePackages = "com.trek.core")
@EnableJpaRepositories(basePackages = "com.trek.core.repository")
public class TrekkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrekkerApplication.class, args);
    }
}