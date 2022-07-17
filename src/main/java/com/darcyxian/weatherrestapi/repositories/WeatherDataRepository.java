package com.darcyxian.weatherrestapi.repositories;

import com.darcyxian.weatherrestapi.entites.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Darcy Xian  17/7/22  10:30 am      weatherRestAPI
 */
public interface WeatherDataRepository extends JpaRepository<WeatherEntity,Long> {
    Optional<WeatherEntity> findByCityNameAndCountryName (String cityName, String countryName);
}
