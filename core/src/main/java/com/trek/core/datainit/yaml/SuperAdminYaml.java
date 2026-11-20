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
@ConfigurationProperties(prefix = "super-user")
@PropertySource(
        factory = YamlPropertySourceFactory.class,
        value = "classpath:initialize/super-user.yml")
public class SuperAdminYaml {

    private Admin admin;
    private Role role;
    private List<AuthorityYaml> authorities;

    @Getter
    @Setter
    public static class Admin {
        private String name;
        private String username;
        private String email;
        private String password;
        private String superPassword;
        private Boolean isVerified;
        private Boolean isSuperAdmin;
    }

    @Getter @Setter
    public static class Role {
        private String name;
        private String description;
    }

    @Getter @Setter
    public static class AuthorityYaml {
        private String code;
        private String description;
    }
}
