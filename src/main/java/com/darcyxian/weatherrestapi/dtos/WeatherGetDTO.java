package com.darcyxian.weatherrestapi.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Darcy Xian  17/7/22  9:29 am      weatherRestAPI
 */
@Getter
@Setter
public class WeatherGetDTO {
    private String cityName;
    private String countryName;
    private String weatherDes;
}
