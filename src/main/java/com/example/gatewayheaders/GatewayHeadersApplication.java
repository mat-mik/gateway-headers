package com.example.gatewayheaders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayHeadersApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayHeadersApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/cache")
                        .uri("http://localhost:9999/cache"))
                .build();
    }

}
