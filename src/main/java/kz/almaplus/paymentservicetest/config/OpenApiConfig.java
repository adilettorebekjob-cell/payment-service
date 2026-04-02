package kz.almaplus.paymentservicetest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment Service REST API")
                        .version("1.0.0")
                        .description("Simple payment service for creating, checking status, confirming and canceling payments. Built with Spring Boot 4.0.5.")
                        .contact(new Contact()
                                .name("Senior Java Developer")
                                .email("developer@example.com")));
    }
}