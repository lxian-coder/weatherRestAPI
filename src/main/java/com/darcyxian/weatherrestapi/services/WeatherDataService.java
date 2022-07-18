package com.darcyxian.weatherrestapi.services;

import com.darcyxian.weatherrestapi.dtos.WeatherBulkDTO;
import com.darcyxian.weatherrestapi.dtos.WeatherDataDTO;
import com.darcyxian.weatherrestapi.exceptions.NoWeatherDataException;
import com.darcyxian.weatherrestapi.exceptions.ResourceNotFoundException;
import com.darcyxian.weatherrestapi.mappers.WeatherMapper;
import com.darcyxian.weatherrestapi.repositories.WeatherDataRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Darcy Xian  17/7/22  10:43 am      weatherRestAPI
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepo;
    private final WeatherMapper weatherMapper;
    @Value("${weathermap.service.key}")
    private String webMapKey;
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    @SneakyThrows
    public void updateWeatherDB(String cityName, String countryName) {
        log.info("updateWeatherDB() started");
        URI uri = formUri(cityName, countryName);
        HttpResponse<String> res = queryWeatherWebsite(uri);

        String weatherDes = extractWeatherDes(res.body());
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO(cityName, countryName, weatherDes);

        weatherDataRepo.findByCityNameAndCountryName(cityName, countryName).map(weatherEntity -> {
            if (!weatherEntity.getWeatherDescription().equals(weatherDataDTO.getWeatherDescription())) {
                weatherEntity.setWeatherDescription(weatherDataDTO.getWeatherDescription());
                return weatherDataRepo.save(weatherEntity);
            }
            return weatherEntity;
        }).orElseGet(() -> weatherDataRepo.save(weatherMapper.toEntity(weatherDataDTO)));
    }

    @SneakyThrows
    public URI formUri(String cityName, String countryName) {
        HttpGet httpGet = new HttpGet(baseUrl);
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter("q", cityName.concat(",").concat(countryName))
                .addParameter("appid", webMapKey)
                .build();
        return uri;
    }

    @SneakyThrows
    public HttpResponse<String> queryWeatherWebsite(URI uri) {
        log.info("queryWeatherWebsite() started");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.of(5, ChronoUnit.SECONDS))
                .GET()
                .build();
        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() == 404) {
            log.info("No weather data retrieved from weatherMap website for the city user searched.");
            throw new NoWeatherDataException("There is no weather data for city you searched");
        }
        return res;
    }

    public String extractWeatherDes(String weatherStringIfo) {
        Gson gson = new Gson();
        WeatherBulkDTO weatherBulkDTO = gson.fromJson(weatherStringIfo, WeatherBulkDTO.class);
        String weatherDescription = weatherBulkDTO.getWeather().get(0).getDescription();
        log.info("Weather description extracted: {}", weatherDescription);
        return weatherDescription;
    }

    public WeatherDataDTO findWeatherData(String cityName, String countryName) {
        log.info("findWeatherData() started");
        WeatherDataDTO weatherDataDTO = weatherDataRepo.findByCityNameAndCountryName(cityName, countryName).map(
                weatherDataEntity -> weatherMapper.fromEntity(weatherDataEntity)
        ).orElseThrow(() -> {
            log.error("Can not find weather data in DB.");
            return new ResourceNotFoundException("Error, Can not find weather data in DB.");
        });
        return weatherDataDTO;
    }

}
