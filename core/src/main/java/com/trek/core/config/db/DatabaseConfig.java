package com.trek.core.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author udaya
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    private final DataSourceProperties dataSourceProperties;

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        log.info("Initializing HikariCp DataSource");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProperties.getUrl());
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());

        hikariConfig.setMaximumPoolSize(dataSourceProperties.getHikariConfig().getMaximumPoolSize());
        hikariConfig.setMinimumIdle(dataSourceProperties.getHikariConfig().getMinimumIdle());
        hikariConfig.setConnectionTimeout(dataSourceProperties.getHikariConfig().getConnectionTimeout());
        hikariConfig.setIdleTimeout(dataSourceProperties.getHikariConfig().getIdleTimeout());
        hikariConfig.setMaxLifetime(dataSourceProperties.getHikariConfig().getMaxLifetime());
        hikariConfig.setPoolName(dataSourceProperties.getHikariConfig().getPoolName());
        hikariConfig.setAutoCommit(dataSourceProperties.getHikariConfig().isAutoCommit());

        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setLeakDetectionThreshold(60000);

        hikariConfig.addDataSourceProperty("reWriteBatchedInserts", "true");

        log.info("DataSource configured with pool size: {}",
                dataSourceProperties.getHikariConfig().getMaximumPoolSize());

        return new HikariDataSource(hikariConfig);
    }
}
