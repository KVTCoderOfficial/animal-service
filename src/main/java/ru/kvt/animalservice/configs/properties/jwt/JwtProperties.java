package ru.kvt.animalservice.configs.properties.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "jwt")
@ConfigurationPropertiesScan
@Configuration
public class JwtProperties {

    @NotNull
    private String secret;

    private Integer lifetime;

}
