package com.example.testDrivenDevelopementWithJUnit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JUnit TDD Test Book")
                        .description("By Jahidul Arafat, Solution Architect, Oracle")
                        .version("0.0.1"));
    }
}
