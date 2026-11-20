package com.trek.core.datainit.yaml;

import com.trek.core.util.YamlPropertySourceFactory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "trekker-role")
@PropertySource(
        factory = YamlPropertySourceFactory.class,
        value = "classpath:initialize/trekker-role.yml")
public class TrekkerUserYaml {
    private Role role;
    private List<AuthorityYaml> authorities;

    @Getter
    @Setter
    public static class Role {
        private String name;
        private String description;
    }

    @Getter
    @Setter
    public static class AuthorityYaml {
        private String code;
        private String description;
    }
}
