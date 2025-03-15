package com.its.notificationservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/its/v3/api-docs").and().method(HttpMethod.GET)
                        .uri("lb://issue-tracking-service"))
                .route("issue-tracking-service", r -> r.path("/api/its/**")
                        .uri("lb://issue-tracking-service"))
                .build();
    }
}
