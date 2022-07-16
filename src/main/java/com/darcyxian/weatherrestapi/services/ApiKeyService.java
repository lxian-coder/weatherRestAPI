package com.darcyxian.weatherrestapi.services;

import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import com.darcyxian.weatherrestapi.exceptions.ResourceNotFoundException;
import com.darcyxian.weatherrestapi.repositories.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Darcy Xian  15/7/22  11:06 pm      weatherRestAPI
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public Optional<ApiKeyEntity> findByKeyValue(String keyValue) {
        log.info("findByKeyValue() started.");
        return apiKeyRepository.findByKeyValue(keyValue);
    }

    public ApiKeyEntity updateCountTimes(Long id) {
        log.info("updateCountTimes() started.");
        return apiKeyRepository.findById(id).map(apiKey -> {
            apiKey.setCountTimes(apiKey.getCountTimes() + 1);
            return apiKeyRepository.save(apiKey);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not find ApiKeyEntity in updateCountTimes() with ID:" + id));
    }

    public ApiKeyEntity refreshApiKeyRecord(Long id) {
        log.info("refreshApiKeyRecord() started.");
        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        long oneHour = 1000L * 3600L;

        return apiKeyRepository.findById(id).map(apiKey -> {
            apiKey.setCountTimes(1);
            apiKey.setRefreshTime(currentTime + oneHour);
            return apiKeyRepository.save(apiKey);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not find ApiKeyEntity in refreshApiKeyRecord() with ID:" + id));
    }

}
