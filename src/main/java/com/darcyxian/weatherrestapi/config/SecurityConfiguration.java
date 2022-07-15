package com.darcyxian.weatherrestapi.config;

import com.darcyxian.weatherrestapi.WeatherRestApiApplication;
import com.darcyxian.weatherrestapi.repositories.ApiKeyRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.SecretKey;

/**
 * Darcy Xian  13/7/22  11:29 pm      weatherRestAPI
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
//public class SecurityConfiguration {
//    private final ApiKeyRepository apiKeyRepository;
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
//
//        httpSecurity
//                .cors()
//                .and()
//                .headers().frameOptions().sameOrigin()
//                .and()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterAfter(new ApiKeyFilter(apiKeyRepository), BasicAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll()
//                .anyRequest()
//                .authenticated();
//
//        return httpSecurity.build();
//
//    }
//
//}

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final ApiKeyRepository apiKeyRepository;

    @Value("${http.auth-token-header-name}")
    private String principalRequestHeader;
    private String principalRequestValue = "demoKey1";

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        APIKeyAuthFilter apiKeyAuthFilter = new APIKeyAuthFilter(principalRequestHeader);
        apiKeyAuthFilter.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                if(!principalRequestValue.equals(principal)){
                    throw new BadCredentialsException("The API key is wrong.");
                }
                authentication.setAuthenticated(true);
                return authentication;
            }
        });
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



    }

}





