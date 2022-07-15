package com.darcyxian.weatherrestapi.config;

import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import com.darcyxian.weatherrestapi.repositories.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Darcy Xian  14/7/22  2:30 pm      weatherRestAPI
 */
@Slf4j
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

   private final ApiKeyRepository apiKeyRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.debug("doFilterInternal() started");
        String apiKeyHeader = request.getHeader("ApiKey");
        if(apiKeyHeader == null || apiKeyHeader.length() == 0){
            log.info("No ApiKeyHeader provided");
            filterChain.doFilter(request,response);
            return;
        }
            log.info("API passed: {}", apiKeyHeader);
            Optional<ApiKeyEntity> res = apiKeyRepo.findByKeyValue(apiKeyHeader);
            // Optional<ApiKeyEntity> res2 = apiKeyRepo.findById(1L);
            // log.info("res.keyValue");
           log.info("res.keyValue: {}",res.get().getKeyValue());

      //  SecurityContextHolder.getContext().setAuthentication();


         log.info("!!!!!!!!!!!");
         filterChain.doFilter(request,response);
    }
}
