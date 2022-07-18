package com.darcyxian.weatherrestapi.services;

import com.darcyxian.weatherrestapi.dtos.WeatherDataDTO;
import com.darcyxian.weatherrestapi.entites.WeatherDataEntity;
import com.darcyxian.weatherrestapi.exceptions.NoWeatherDataException;
import com.darcyxian.weatherrestapi.mappers.WeatherMapper;
import com.darcyxian.weatherrestapi.repositories.WeatherDataRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Darcy Xian  17/7/22  8:53 pm      weatherRestAPI
 */
@ExtendWith(MockitoExtension.class)
class WeatherDataServiceTest {

    @Mock
    WeatherDataRepository weatherDataRepo;

    @Mock
    WeatherMapper weatherMapper;

    @InjectMocks
    WeatherDataService weatherDataService;

    private WeatherDataEntity weatherDataEntityTest;
    private WeatherDataEntity weatherDataEntityTest2;
    private WeatherDataDTO weatherDataDTOTest;
    private WeatherDataDTO weatherDataDTOTest2;
    private String WEATHER_RESPONSE;
    private String TEST_CITY;
    private String TEST_COUNTRY;
    private String TEST_DES;
    private String TEST_DES2;
    private URI uri;
    private HttpResponse<String> response;

    @BeforeEach
    void setUp() {
        WEATHER_RESPONSE = "{\"coord\":{\"lon\":-74.006,\"lat\":40.7143},\"weather\":[{\"id\":800,\"main\":\"Clear\"," +
                "\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":" +
                "300.74,\"feels_like\":303.04,\"temp_min\":298.7,\"temp_max\":304.02,\"pressure\":1015," +
                "\"humidity\":70},\"visibility\":10000,\"wind\":{\"speed\":5.66,\"deg\":180},\"clouds\":" +
                "{\"all\":0},\"dt\":1658096596,\"sys\":{\"type\":2,\"id\":2039034,\"country\":\"US\"," +
                "\"sunrise\":1658050746,\"sunset\":1658103891},\"timezone\":-14400,\"id\":5128581,\"name\":" +
                "\"New York\",\"cod\":200}";

        TEST_CITY = "testCity";
        TEST_COUNTRY = "testCountry";
        TEST_DES = "clear sky";
        TEST_DES2 = "Sunshine";

        weatherDataEntityTest = new WeatherDataEntity();
        weatherDataEntityTest.setId(1L);
        weatherDataEntityTest.setWeatherDescription(TEST_DES);
        weatherDataEntityTest.setCityName(TEST_CITY);
        weatherDataEntityTest.setCountryName(TEST_COUNTRY);

        weatherDataEntityTest2 = new WeatherDataEntity();
        weatherDataEntityTest2.setId(1L);
        weatherDataEntityTest2.setWeatherDescription(TEST_DES2);
        weatherDataEntityTest2.setCityName(TEST_CITY);
        weatherDataEntityTest2.setCountryName(TEST_COUNTRY);

        weatherDataDTOTest = new WeatherDataDTO(TEST_CITY, TEST_COUNTRY, TEST_DES);
        weatherDataDTOTest2 = new WeatherDataDTO(TEST_CITY, TEST_COUNTRY, TEST_DES2);
        uri = URI.create("testURI");
        response = new HttpResponse<>() {
            @Override
            public int statusCode() { return 0; }
            @Override
            public HttpRequest request() { return null; }
            @Override
            public Optional<HttpResponse<String>> previousResponse() { return Optional.empty(); }
            @Override
            public HttpHeaders headers() { return null; }
            @Override
            public String body() { return WEATHER_RESPONSE; }
            @Override
            public Optional<SSLSession> sslSession() { return Optional.empty(); }
            @Override
            public URI uri() { return null; }
            @Override
            public HttpClient.Version version() { return null; }
        };
    }

    @Test
    void shouldReturnURIWithParametersGivenCityAndCountryName() throws URISyntaxException {
        String expectUri = "https://api.openweathermap.org/data/2.5/weather?q=New+York%2CUS&appid";
        String formedUri = weatherDataService.formUri("New York", "US").toString();
        assertNotNull(formedUri);
        assertEquals(expectUri, formedUri);
    }

    @Test
    void shouldRetriveWeatherDataGivenDataExist() {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(WEATHER_RESPONSE)
                .setResponseCode(200));
        URI uri = mockWebServer.url("/test").uri();
        HttpResponse<String> res = weatherDataService.queryWeatherWebsite(uri);
        assertNotNull(res);
        assertEquals(WEATHER_RESPONSE, res.body());
    }

    @Test
    void shouldThrowNoWeatherDataExcetptionGivenNoDataExist() {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setResponseCode(404));
        URI uri = mockWebServer.url("/test").uri();
        assertThrows(NoWeatherDataException.class, () -> weatherDataService.queryWeatherWebsite(uri));
    }

    @Test
    void shouldReturnWeatherDescriptionGivenWeatherInfoString() throws JSONException {
        String res = weatherDataService.extractWeatherDes(WEATHER_RESPONSE);
        assertNotNull(res);
        assertEquals(TEST_DES, res);
    }

    @Test
    void shouldNotUpdateWeatherEntityGiveNewDataSameAsOld() {
        WeatherDataService weatherDataService1 = spy(weatherDataService);

        doReturn(uri).when(weatherDataService1).formUri(TEST_CITY, TEST_COUNTRY);
        doReturn(response).when(weatherDataService1).queryWeatherWebsite(uri);

        weatherDataService1.updateWeatherDB(TEST_CITY, TEST_COUNTRY);

        verify(weatherDataService1, times(1)).formUri(TEST_CITY, TEST_COUNTRY);
        verify(weatherDataService1, times(1)).queryWeatherWebsite(uri);
        verify(weatherDataService1, times(1)).extractWeatherDes(response.body());
        verify(weatherDataRepo, times(1)).findByCityNameAndCountryName(TEST_CITY, TEST_COUNTRY);
        verify(weatherDataRepo, times(0)).save(weatherDataEntityTest);
        verify(weatherMapper, times(0)).toEntity(weatherDataDTOTest);
        verify(weatherDataRepo, times(0)).save(weatherDataEntityTest2);
    }

    @Test
    void shouldUpdateWeatherEntityGiveNewDataDifferentFromOld() {
        WeatherDataService weatherDataService1 = spy(weatherDataService);

        doReturn(uri).when(weatherDataService1).formUri(TEST_CITY, TEST_COUNTRY);
        doReturn(response).when(weatherDataService1).queryWeatherWebsite(uri);
        doReturn(TEST_DES2).when(weatherDataService1).extractWeatherDes(response.body());
        when(weatherDataRepo.findByCityNameAndCountryName(TEST_CITY, TEST_COUNTRY)).thenReturn(Optional.of(weatherDataEntityTest));

        weatherDataService1.updateWeatherDB(TEST_CITY, TEST_COUNTRY);

        verify(weatherDataService1, times(1)).formUri(TEST_CITY, TEST_COUNTRY);
        verify(weatherDataService1, times(1)).queryWeatherWebsite(uri);
        verify(weatherDataService1, times(1)).extractWeatherDes(response.body());
        verify(weatherDataRepo, times(1)).findByCityNameAndCountryName(TEST_CITY, TEST_COUNTRY);
        verify(weatherDataRepo, times(1)).save(weatherDataEntityTest);
        verify(weatherMapper, times(0)).toEntity(weatherDataDTOTest);
        verify(weatherDataRepo, times(0)).save(weatherDataEntityTest2);
    }

    @Test
    void shouldSaveWeatherEntityGiveNoOldDataFound() {
        WeatherDataService weatherDataService1 = spy(weatherDataService);

        doReturn(uri).when(weatherDataService1).formUri(TEST_CITY, TEST_COUNTRY);
        doReturn(response).when(weatherDataService1).queryWeatherWebsite(uri);
        doReturn(TEST_DES2).when(weatherDataService1).extractWeatherDes(response.body());
        when(weatherDataRepo.findByCityNameAndCountryName(TEST_CITY, TEST_COUNTRY)).thenReturn(Optional.empty());
        when(weatherMapper.toEntity(any(WeatherDataDTO.class))).thenReturn(weatherDataEntityTest2);
        weatherDataService1.updateWeatherDB(TEST_CITY, TEST_COUNTRY);

        verify(weatherDataService1, times(1)).formUri(TEST_CITY, TEST_COUNTRY);
        verify(weatherDataService1, times(1)).queryWeatherWebsite(uri);
        verify(weatherDataService1, times(1)).extractWeatherDes(response.body());
        verify(weatherDataRepo, times(1)).findByCityNameAndCountryName(TEST_CITY, TEST_COUNTRY);
        verify(weatherDataRepo, times(0)).save(weatherDataEntityTest);
        verify(weatherMapper, times(1)).toEntity(any(WeatherDataDTO.class));
        verify(weatherDataRepo, times(1)).save(weatherDataEntityTest2);
    }

    @Test
    void shouldReturnWeatherDataEntityGivenItExist() {
        when(weatherDataRepo.findByCityNameAndCountryName(TEST_CITY, TEST_COUNTRY)).thenReturn(Optional.of(weatherDataEntityTest));
        when(weatherMapper.fromEntity(weatherDataEntityTest)).thenReturn(weatherDataDTOTest);

        WeatherDataDTO weatherDataDTO = weatherDataService.findWeatherData(TEST_CITY, TEST_COUNTRY);
        assertNotNull(weatherDataDTO);
        assertEquals(TEST_DES, weatherDataDTO.getWeatherDescription());
        assertEquals(TEST_CITY, weatherDataDTO.getCityName());
        assertEquals(TEST_COUNTRY, weatherDataDTO.getCountryName());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionGivenDataNotExist() {
        when(weatherDataRepo.findByCityNameAndCountryName(TEST_CITY, TEST_COUNTRY)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> weatherDataService.findWeatherData(TEST_CITY, TEST_COUNTRY));
    }


}