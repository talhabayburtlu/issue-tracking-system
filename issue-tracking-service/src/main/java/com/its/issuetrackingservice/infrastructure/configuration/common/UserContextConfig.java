package com.its.issuetrackingservice.infrastructure.configuration.common;

import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.filters.UserContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Configuration
public class UserContextConfig {

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    @Bean
    public FilterRegistrationBean<UserContextFilter> userContextFilterRegistration() {
        FilterRegistrationBean<UserContextFilter> result = new FilterRegistrationBean<>();
        result.setFilter(this.userContextFilter());
        result.setUrlPatterns(List.of("/*"));
        result.setName("User Context Filter");
        result.setOrder(1);
        return result;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserContext userContext() {
        return (UserContext) RequestContextHolder.getRequestAttributes().getAttribute("userContext", RequestAttributes.SCOPE_REQUEST);
    }

}
