package com.darcyxian.weatherrestapi.controller;

import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import com.darcyxian.weatherrestapi.repositories.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Darcy Xian  15/7/22  1:59 pm      weatherRestAPI
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class test {
    private final ApiKeyRepository apiKeyRepository;
    @GetMapping
    public ResponseEntity<ApiKeyEntity> getAllAccounts() {
        log.debug("controller getAllAccounts() started!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ApiKeyEntity res = apiKeyRepository.findByKeyValue("demoKey1").get();
        return ResponseEntity.ok(res);
    }
}
