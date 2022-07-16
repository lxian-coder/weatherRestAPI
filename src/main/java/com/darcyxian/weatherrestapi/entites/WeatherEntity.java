package com.darcyxian.weatherrestapi.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Darcy Xian  17/7/22  9:02 am      weatherRestAPI
 */
@Entity
@Getter
@Setter
@Table(name = "weather_data")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "weather_description")
    private String weatherDes;
}
