package com.darcyxian.weatherrestapi.controllers;

import com.darcyxian.weatherrestapi.dtos.WeatherDataDTO;
import com.darcyxian.weatherrestapi.exceptions.InvalidInputException;
import com.darcyxian.weatherrestapi.services.WeatherDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Darcy Xian  17/7/22  11:15 am      weatherRestAPI
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherDataController {
    private final WeatherDataService weatherDataService;

    @GetMapping(value = {"", "/{cityName}", "/{cityName}/{countryName}"})
    public ResponseEntity<WeatherDataDTO> findCurrentWeather(@PathVariable(required = false) String cityName, @PathVariable(required = false) String countryName) throws IOException, URISyntaxException, ExecutionException, InterruptedException {

        if (StringUtils.isBlank(cityName) || StringUtils.isBlank(countryName)) {
            throw new InvalidInputException("Invalid input. City Name and Country name should not be null or empty.");
        }
        weatherDataService.updateWeatherDB(cityName, countryName);
        WeatherDataDTO weatherDataDTO = weatherDataService.findWeatherData(cityName, countryName);
        return ResponseEntity.ok(weatherDataDTO);
    }

}
