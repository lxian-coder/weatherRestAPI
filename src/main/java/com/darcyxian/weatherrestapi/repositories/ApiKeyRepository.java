package com.darcyxian.weatherrestapi.repositories;

import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Darcy Xian  15/7/22  10:55 am      weatherRestAPI
 */
@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {

    Optional<ApiKeyEntity> findByKeyValue(String keyValue);
    Optional<ApiKeyEntity> findById (long id);
}
