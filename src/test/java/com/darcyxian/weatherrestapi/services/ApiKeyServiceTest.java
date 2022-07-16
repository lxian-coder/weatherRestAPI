package com.darcyxian.weatherrestapi.services;

import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import com.darcyxian.weatherrestapi.exceptions.ResourceNotFoundException;
import com.darcyxian.weatherrestapi.repositories.ApiKeyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Darcy Xian  16/7/22  4:43 pm      weatherRestAPI
 */
@ExtendWith(MockitoExtension.class)
class ApiKeyServiceTest {

    @Mock
    private ApiKeyRepository apiKeyRepo;

    @InjectMocks
    private ApiKeyService apiKeyService;

    private ApiKeyEntity apiKeyEntity;
    @BeforeEach
    void setUp() {
        apiKeyEntity = new ApiKeyEntity(1L,"testKeyInService",3,1112L);
    }

    @Test
    void shouldReturnApiKeyEntityGivenValidKeyValue() {
        when(apiKeyRepo.findByKeyValue("testKeyInService")).thenReturn(Optional.of(apiKeyEntity));

        Optional<ApiKeyEntity> apiKeyEntityOp = apiKeyService.findByKeyValue("testKeyInService");
        assertTrue(apiKeyEntityOp.isPresent());
        assertEquals(3,apiKeyEntityOp.get().getCountTimes());
        assertEquals(1112L, apiKeyEntityOp.get().getRefreshTime());
    }

    @Test
    void shouldThrowExceptionGivenInvalidId() {
        when(apiKeyRepo.findById(any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> apiKeyService.updateCountTimes(any()));
        assertThrows(ResourceNotFoundException.class, () -> apiKeyService.refreshApiKeyRecord(any()));
    }

    @Test
    void shouldUpdateCountTimesGivenInvalidId(){
        ApiKeyEntity apiKeyEntity2 =  new ApiKeyEntity(1L,"testKeyInService",4,1112L);
        when(apiKeyRepo.findById(any())).thenReturn(Optional.of(apiKeyEntity));
        when(apiKeyRepo.save(any(ApiKeyEntity.class))).thenReturn(apiKeyEntity2);

        ApiKeyEntity apiKeyEntity = apiKeyService.updateCountTimes(any());
        verify(apiKeyRepo,times(1)).findById(any());
        verify(apiKeyRepo,times(1)).save(any(ApiKeyEntity.class));
        assertEquals(1L,apiKeyEntity.getId());
        assertEquals(4,apiKeyEntity.getCountTimes());
        assertEquals("testKeyInService",apiKeyEntity.getKeyValue());
    }

    @Test
    void shouldRefreshApiKeyEntityGivenNewApiKeyEntity(){
        ApiKeyEntity apiKeyEntity3 =  new ApiKeyEntity(1L,"testKeyInService",1,2222L);
        when(apiKeyRepo.findById(any())).thenReturn(Optional.of(apiKeyEntity));
        when(apiKeyRepo.save(any(ApiKeyEntity.class))).thenReturn(apiKeyEntity3);

        ApiKeyEntity apiKeyEntity = apiKeyService.refreshApiKeyRecord(any());
        verify(apiKeyRepo,times(1)).findById(any());
        verify(apiKeyRepo,times(1)).save(any(ApiKeyEntity.class));
        assertEquals(1L,apiKeyEntity.getId());
        assertEquals(1,apiKeyEntity.getCountTimes());
        assertEquals("testKeyInService",apiKeyEntity.getKeyValue());
    }
}