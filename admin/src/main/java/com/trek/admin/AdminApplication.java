package com.trek.admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.trek")
@EntityScan(basePackages = "com.trek.core")
@EnableJpaRepositories(basePackages = "com.trek.core")
public class AdminApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminApplication.class)
                .properties("spring.config.location=classpath:/")
                .run(args);
    }
}