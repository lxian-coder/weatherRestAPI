package com.darcyxian.weatherrestapi.mappers;

import com.darcyxian.weatherrestapi.dtos.WeatherDataDTO;
import com.darcyxian.weatherrestapi.entites.WeatherDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Darcy Xian  17/7/22  6:12 pm      weatherRestAPI
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeatherMapper {

    WeatherDataDTO fromEntity(WeatherDataEntity weatherDataEntity);
    WeatherDataEntity toEntity(WeatherDataDTO weatherDataDTO);
}
