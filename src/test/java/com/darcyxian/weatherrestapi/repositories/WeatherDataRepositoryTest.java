package com.darcyxian.weatherrestapi.repositories;

import com.darcyxian.weatherrestapi.WeatherRestApiApplication;
import com.darcyxian.weatherrestapi.entites.WeatherDataEntity;
import com.darcyxian.weatherrestapi.utils.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Darcy Xian  17/7/22  9:39 am      weatherRestAPI
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeatherRestApiApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WeatherDataRepositoryTest {

    @Autowired
    private WeatherDataRepository weatherDataRepo;

    @Autowired
    private Utility utility;

    @Test
    void shouldReturnWeatherEntityGivenWeatherEntityHasBeenSaved(){
        // Given
        // Create new weatherEntity
        WeatherDataEntity weatherDataEntity = utility.buildWeatherEntity("testCity1","testCountry1","Cloud");

        // When
        WeatherDataEntity returnedWeather = weatherDataRepo.save(weatherDataEntity);

        // Then
        assertEquals("testCity1",returnedWeather.getCityName());
        assertEquals("testCountry1",returnedWeather.getCountryName());
        assertEquals("Cloud",returnedWeather.getWeatherDescription());
    }

    @Test
    void shouldReturnWeatherEntityGivenValidCityNameAndCountryName(){
        // Given
        // Create new weatherEntity
        WeatherDataEntity weatherDataEntity = utility.buildWeatherEntity("testCity","testCountry","Sunshine");
        weatherDataRepo.save(weatherDataEntity);

        // When
        Optional<WeatherDataEntity> weatherEntityOp = weatherDataRepo.findByCityNameAndCountryName("testCity","testCountry");

        // Then
        assertTrue(weatherEntityOp.isPresent());
        assertEquals("testCity",weatherEntityOp.get().getCityName());
        assertEquals("testCountry",weatherEntityOp.get().getCountryName());
        assertEquals("Sunshine",weatherEntityOp.get().getWeatherDescription());
    }

}
