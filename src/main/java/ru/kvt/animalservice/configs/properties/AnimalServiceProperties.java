package ru.kvt.animalservice.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import ru.kvt.animalservice.configs.properties.auth.AuthProperties;
import ru.kvt.animalservice.configs.properties.jwt.JwtProperties;

@ConfigurationProperties(prefix = "animal-service")
@ConfigurationPropertiesScan
@Configuration
public class AnimalServiceProperties {

    JwtProperties jwtProperties;

    AuthProperties authProperties;

}
