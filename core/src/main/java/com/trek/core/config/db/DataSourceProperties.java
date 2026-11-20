package com.trek.core.config.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author udaya
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.datasource")
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    private HikariConfig hikariConfig = new HikariConfig();

    @Getter
    @Setter
    public static class HikariConfig {
        private int maximumPoolSize = 10;
        private int minimumIdle = 5;
        private long connectionTimeout = 30000;
        private long idleTimeout = 600000;
        private long maxLifetime = 1800000;
        private String poolName = "TrekNepalHikariPool";
        private boolean autoCommit = false;
    }

}
