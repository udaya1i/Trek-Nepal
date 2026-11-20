package com.trek.core.datainit.yaml;

import com.trek.core.util.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties
@PropertySource(
        factory = YamlPropertySourceFactory.class,
        value = "classpath:initialize/location.yml"
)
public class LocationYaml {

    private List<Provinces> provinces;

    @Data
    public static class Provinces {
        private Long id;
        private String name;
        private List<District> districts;
    }

    @Data
    public static class District {
        private Long id;
        private String name;
        private List<LocalLevel> localLevels;
    }

    @Data
    public static class LocalLevel {
        private Long id;
        private String name;
    }

}
