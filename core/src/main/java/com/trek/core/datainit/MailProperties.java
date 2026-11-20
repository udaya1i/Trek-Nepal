package com.trek.core.messaging.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author udaya
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private String from;
    private String host;
    private int port;
    private String username;
    private String password;
    private Properties properties = new Properties();

}