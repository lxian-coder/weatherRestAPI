package com.darcyxian.weatherrestapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Darcy Xian  17/7/22  9:29 am      weatherRestAPI
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataDTO {
    private String cityName;
    private String countryName;
    private String weatherDescription;
}
