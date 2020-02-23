package com.bhanuchaddha.integration.openweatherapiintegration.integration.weather;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.common.RestIntegrationAdapter;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.WeatherResponse;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenWeatherAPIRestIntegration implements OpenWeatherAPI{

    @Qualifier("openWeather")
    private final RestIntegrationAdapter restIntegrationAdapter;

    @Value("${open-weather.api.key}")
    private String apiKey;

    public Mono<WeatherResponse> getWeatherByLocationId(String locationId, TemperatureUnit temperatureUnit) {
        log.info("OpenWeatherAPI is called for location {} and temperatureUnit {}", locationId, temperatureUnit.name());
        return restIntegrationAdapter.get(
                new DefaultUriBuilderFactory().builder()
                        .path("/forecast")
                        .queryParam("units", temperatureUnit.getSystem())
                        .queryParam("appid", apiKey)
                        .queryParam("id", locationId)
                        .build()
                , "get5DayWeatherForecast"
                , WeatherResponse.class
        );


    }
}
