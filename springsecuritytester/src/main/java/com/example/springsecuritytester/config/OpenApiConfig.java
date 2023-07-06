package com.example.springsecuritytester.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger OpenApi to documenting the Spring APIs.
 * Alternative to OpenApi: Spring Fox
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Spring Security Testing API Book")
                        .description("By Jahidul Arafat, Solution Architect, Oracle")
                        .version("0.0.1"));
    }
}
