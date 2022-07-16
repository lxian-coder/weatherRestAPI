package com.darcyxian.weatherrestapi.config;

import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import com.darcyxian.weatherrestapi.services.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Darcy Xian  13/7/22  11:29 pm      weatherRestAPI
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final ApiKeyService apiKeyService;
    @Value("${http.auth-token-header-name}")
    private String principalRequestHeader;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        APIKeyAuthFilter apiKeyAuthFilter = new APIKeyAuthFilter(principalRequestHeader);
        apiKeyAuthFilter.setAuthenticationManager(getAuthenticationManager());

        httpSecurity
                .cors()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(apiKeyAuthFilter)
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest()
                .authenticated();

        return httpSecurity.build();
    }

    AuthenticationManager getAuthenticationManager(){
       return authentication -> {
            String principal = (String) authentication.getPrincipal();
            Optional<ApiKeyEntity> apiKeyEntityOp = apiKeyService.findByKeyValue(principal);
            if(!apiKeyEntityOp.isPresent()){
                log.warn("Access is denied because API key provided by users is wrong.");
                throw new BadCredentialsException("The API key is wrong.");
            }
            ApiKeyEntity apiKeyEntity = apiKeyEntityOp.get();
            int keyBeUsedCount = apiKeyEntity.getCountTimes();
            long refreshTime = apiKeyEntity.getRefreshTime();
            long currentTime = new Timestamp(System.currentTimeMillis()).getTime();

            if(refreshTime >= currentTime && keyBeUsedCount >= 5){
                log.warn("Access is denied because API key has been used 5 times in one hout.");
                throw new BadCredentialsException("Sorry, you can only query 5 times per hour.");
            }
            if(refreshTime >= currentTime && keyBeUsedCount < 5){
                apiKeyService.updateCountTimes(apiKeyEntity.getId());
            }
            if(refreshTime < currentTime){
                apiKeyService.refreshApiKeyRecord(apiKeyEntity.getId());
            }
            log.info("API key accepted!");
            authentication.setAuthenticated(true);
            return authentication;
        };
    }

};








