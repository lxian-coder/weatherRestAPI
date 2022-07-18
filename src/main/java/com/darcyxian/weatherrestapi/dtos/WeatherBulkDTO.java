package com.darcyxian.weatherrestapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Darcy Xian  17/7/22  1:29 pm      weatherRestAPI
 */
@Getter
@Setter
public class WeatherBulkDTO {
    private List<WeatherDTO> weather;
}

