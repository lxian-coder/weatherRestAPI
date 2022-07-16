package com.darcyxian.weatherrestapi.repositories;

import com.darcyxian.weatherrestapi.WeatherRestApiApplication;
import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import com.darcyxian.weatherrestapi.utils.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Darcy Xian  16/7/22  4:20 pm      weatherRestAPI
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeatherRestApiApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApiKeyRepositoryTest {

    @Autowired
    private ApiKeyRepository apiKeyRepo;

    @Autowired
    private Utility utility;

    @Test
    void shouldReturnApikeyEntityGivenCorrectKeyValue(){
        //Given
        //Create new apiKeyEntity
        ApiKeyEntity apiKeyEntity1 = utility.buildApiKeyEntity("testKey1",1,111222L);
        apiKeyRepo.save(apiKeyEntity1);

        ApiKeyEntity apiKeyEntity2 = utility.buildApiKeyEntity("testKey2",2,222111L);
        apiKeyRepo.save(apiKeyEntity2);

        // When
        Optional<ApiKeyEntity> savedApiKeyOp = apiKeyRepo.findByKeyValue("testKey2");
        Optional<ApiKeyEntity> savedApiKeyOp2 = apiKeyRepo.findByKeyValue("testKey1");

        // Then
        assertTrue(savedApiKeyOp.isPresent());
        assertEquals(2,savedApiKeyOp.get().getCountTimes());
        assertEquals(222111L,savedApiKeyOp.get().getRefreshTime());

        assertTrue(savedApiKeyOp2.isPresent());
        assertEquals(1,savedApiKeyOp2.get().getCountTimes());
        assertEquals(111222L,savedApiKeyOp2.get().getRefreshTime());
    }

    @Test
    void shouldReturnApikeyEntityGivenCorrectID(){
        // Given
        // Create new apiKeyEntity
        ApiKeyEntity apiKeyEntity3 = utility.buildApiKeyEntity("testKey3",3,333222111L);
        apiKeyRepo.save(apiKeyEntity3);
        Optional<ApiKeyEntity> res = apiKeyRepo.findByKeyValue("testKey3");

        // When
        Optional<ApiKeyEntity> savedApiKeyOp = apiKeyRepo.findById(res.get().getId());

        // Then
        assertTrue(savedApiKeyOp.isPresent());
        assertEquals(3,savedApiKeyOp.get().getCountTimes());
        assertEquals("testKey3",savedApiKeyOp.get().getKeyValue());
        assertEquals(333222111L,savedApiKeyOp.get().getRefreshTime());
    }




}