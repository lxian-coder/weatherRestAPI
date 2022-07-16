package com.darcyxian.weatherrestapi.config;

import com.darcyxian.weatherrestapi.dtos.ErrorDTO;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.logger.debug("Cleared security context due to exception", failed);
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, failed);

        ErrorDTO errorDTO = new ErrorDTO(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
        String errorJsonString = new Gson().toJson(errorDTO);
        PrintWriter out = response.getWriter();
        response.setContentType("Application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        out.print(errorJsonString);
        out.flush();
    }
}


