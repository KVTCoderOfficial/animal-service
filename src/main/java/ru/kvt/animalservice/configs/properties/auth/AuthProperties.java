package ru.kvt.animalservice.configs.properties.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "auth")
@ConfigurationPropertiesScan
@Configuration
public class AuthProperties {

    private Integer attemptsLimit;

    private Integer attemptsLimitTimeFrame;

}
