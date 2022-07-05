package ru.kvt.animalservice.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt")
@ConfigurationPropertiesScan
@Configuration
public class JwtProperties {

    private Integer lifetime;

    private String secret;

}
