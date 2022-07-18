package com.darcyxian.weatherrestapi.utils;

import com.darcyxian.weatherrestapi.entites.ApiKeyEntity;
import com.darcyxian.weatherrestapi.entites.WeatherDataEntity;
import org.springframework.stereotype.Component;

/**
 * Darcy Xian  16/7/22  9:05 pm      weatherRestAPI
 */
@Component
public class Utility {

    public ApiKeyEntity buildApiKeyEntity(String keyValue,
                                          Integer countTimes,
                                          Long refreshTime){
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setKeyValue(keyValue);
        apiKeyEntity.setRefreshTime(refreshTime);
        apiKeyEntity.setCountTimes(countTimes);
        return apiKeyEntity;
    }

    public WeatherDataEntity buildWeatherEntity(String cityName,
                                                String countryName,
                                                String weatherDes){
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
        weatherDataEntity.setCityName(cityName);
        weatherDataEntity.setCountryName(countryName);
        weatherDataEntity.setWeatherDescription(weatherDes);
        return weatherDataEntity;
    }
}
