package com.darcyxian.weatherrestapi.controllers;

import com.darcyxian.weatherrestapi.dtos.WeatherDataDTO;
import com.darcyxian.weatherrestapi.services.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Darcy Xian  18/7/22  7:08 pm      weatherRestAPI
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherDataController.class)
class WeatherDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherDataService weatherDataService;

    @Test
    @WithMockUser
    void shouldReturnWeatherDataDTOGIvenItExist() throws Exception {
        String TEST_CITY = "testCity";
        String TEST_COUNTRY  = "testCountry";
        String WEATHER_DES = "clear sky";
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO(TEST_CITY,TEST_COUNTRY,WEATHER_DES);

        when(weatherDataService.findWeatherData(TEST_CITY,TEST_COUNTRY)).thenReturn(weatherDataDTO);
        mockMvc.perform(get("/weather/testCity/testCountry"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").exists())
                .andExpect(jsonPath("$.cityName").value(TEST_CITY))
                .andExpect(jsonPath("$.countryName").exists())
                .andExpect(jsonPath("$.countryName").value(TEST_COUNTRY))
                .andExpect(jsonPath("$.weatherDescription").exists())
                .andExpect(jsonPath("$.weatherDescription").value(WEATHER_DES));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorCodGivenMissingCityNameOrCounrtyName() throws Exception {

        mockMvc.perform(get("/weather/testCity/"))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get("/weather/"))
                .andExpect(status().is4xxClientError());
    }
}