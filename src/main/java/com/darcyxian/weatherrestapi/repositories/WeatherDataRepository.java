package com.darcyxian.weatherrestapi.repositories;

import com.darcyxian.weatherrestapi.entites.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Darcy Xian  17/7/22  10:30 am      weatherRestAPI
 */
public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity,Long> {
    Optional<WeatherDataEntity> findByCityNameAndCountryName (String cityName, String countryName);
}
