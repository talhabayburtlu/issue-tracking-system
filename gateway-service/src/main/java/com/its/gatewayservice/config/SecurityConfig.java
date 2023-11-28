package com.its.gatewayservice.config;

import com.google.common.base.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/gateway").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public UserHeaderFilter userHeaderFilter() {
        return new UserHeaderFilter();
    }

    public static class UserHeaderFilter implements GlobalFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            return ReactiveSecurityContextHolder.getContext()
                    .filter(c -> c.getAuthentication() != null)
                    .flatMap(c -> {
                        JwtAuthenticationToken jwt = (JwtAuthenticationToken) c.getAuthentication();

                        String id = jwt.getName();
                        if (Strings.isNullOrEmpty(id)) {
                            return Mono.error(new AccessDeniedException("Invalid token. User is not present in token."));
                        }

                        ServerHttpRequest request = exchange.getRequest().mutate().header("x-keycloak-id", id).build();

                        return chain.filter(exchange.mutate().request(request).build());
                    })
                    .switchIfEmpty(chain.filter(exchange));
        }
    }
}
