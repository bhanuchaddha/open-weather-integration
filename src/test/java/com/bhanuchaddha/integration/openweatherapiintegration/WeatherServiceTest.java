package com.bhanuchaddha.integration.openweatherapiintegration;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.OpenWeatherAPIDefault;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.Location;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.LocationTemperature;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.LocationWeather;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.WeatherResponse;
import com.bhanuchaddha.integration.openweatherapiintegration.service.WeatherService;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.FindLocationsByIdsAndMinTempRequest;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {

    private OpenWeatherAPIDefault openWeatherAPIDefault = mock(OpenWeatherAPIDefault.class);
    private WeatherService service = new WeatherService(openWeatherAPIDefault);

    @Test
    public void serviceShouldReturnCorrectLocations() {
        when(openWeatherAPIDefault.getWeatherByLocationId("10", TemperatureUnit.Celsius))
                .thenReturn(Mono.just(weatherResponse(10)));
        when(openWeatherAPIDefault.getWeatherByLocationId("11", TemperatureUnit.Celsius))
                .thenReturn(Mono.just(weatherResponse(11)));
        when(openWeatherAPIDefault.getWeatherByLocationId("12", TemperatureUnit.Celsius))
                .thenReturn(Mono.just(weatherResponse(12)));
        when(openWeatherAPIDefault.getWeatherByLocationId("13", TemperatureUnit.Celsius))
                .thenReturn(Mono.just(weatherResponse(13)));
        when(openWeatherAPIDefault.getWeatherByLocationId("14", TemperatureUnit.Celsius))
                .thenReturn(Mono.just(weatherResponse(14)));
        when(openWeatherAPIDefault.getWeatherByLocationId("15", TemperatureUnit.Celsius))
                .thenReturn(Mono.just(weatherResponse(15)));

        int size = service.getLocationsByIdsAndMinTempNextDay(
                FindLocationsByIdsAndMinTempRequest.builder()
                        .locationIds(Arrays.asList("11", "12", "13", "14", "15"))
                        .temperatureUnit(TemperatureUnit.Celsius)
                        .minTemperature(13d)
                        .build())
                .collectList()
                .block()
                .size();
        Assert.isTrue(size == 2, "There should be exactly 2 locations having temperature greater than 13");

    }

    private WeatherResponse weatherResponse(double temperature) {
        return WeatherResponse.builder()
                .code("200")
                .location(Location.builder()
                        .build())
                .locationWeatherForecasts(
                        Arrays.asList(LocationWeather.builder()
                                .date(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                                .locationTemperature(LocationTemperature.builder()
                                        .temperature(temperature)
                                        .build())
                                .build()
                        ))
                .build();
    }
}
