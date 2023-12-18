package com.its.issuetrackingservice.api.common.config;

import com.its.issuetrackingservice.domain.common.dto.UserContext;
import com.its.issuetrackingservice.domain.common.service.auth.GlobalAuthenticationService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class UserContextFilter implements Filter {
    @Autowired
    private GlobalAuthenticationService globalAuthenticationService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String keycloakId = request.getHeader("x-keycloak-id");

        UserContext userContext = globalAuthenticationService.generateUserContext(keycloakId);

        RequestContextHolder.getRequestAttributes().setAttribute("userContext", userContext, RequestAttributes.SCOPE_REQUEST);

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
