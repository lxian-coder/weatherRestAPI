package com.darcyxian.weatherrestapi.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Darcy Xian  15/7/22  4:22 pm      weatherRestAPI
 */
@Slf4j
@AllArgsConstructor
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    private String principalRequestHeader;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(principalRequestHeader);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
