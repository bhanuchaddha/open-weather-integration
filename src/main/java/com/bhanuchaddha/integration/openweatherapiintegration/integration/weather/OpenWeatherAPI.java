package com.bhanuchaddha.integration.openweatherapiintegration.integration.weather;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.WeatherResponse;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import reactor.core.publisher.Mono;

public interface OpenWeatherAPI {
    Mono<WeatherResponse> getWeatherByLocationId(String locationId, TemperatureUnit temperatureUnit);
}


