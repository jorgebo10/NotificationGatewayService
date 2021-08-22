package com.manning.application.notification.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("NotificationPreferenses API")
                        .description("An API for dealing notification preferences for a customer")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

