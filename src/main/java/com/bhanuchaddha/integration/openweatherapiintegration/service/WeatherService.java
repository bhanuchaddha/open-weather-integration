package com.bhanuchaddha.integration.openweatherapiintegration.service;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.OpenWeatherAPIDefault;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.Location;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.LocationWeather;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.WeatherResponse;
import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.LocationDto;
import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.TemperatureForecastDto;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.FindLocationsByIdsAndMinTempRequest;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.FindLocationsByIdsRequest;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.WeatherForecastByLocationIdRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeatherService {

    //private final OpenWeatherAPI openWeatherAPI; // Using Generic RestIntegrationAdapter
    private final OpenWeatherAPIDefault openWeatherAPIDefault;

    public Flux<LocationDto> getLocationsByIds(FindLocationsByIdsRequest request) {
        return Flux.fromIterable(request.getLocationIds())
                .filter(s -> !Strings.isEmpty(s))
                .flatMap(location -> openWeatherAPIDefault.getWeatherByLocationId(location, request.getTemperatureUnit()))
                .map(WeatherResponse::getLocation)
                .map(Location::toLocationDto);

    }

    public Flux<LocationDto> getLocationsByIdsAndMinTempNextDay(FindLocationsByIdsAndMinTempRequest request) {
        return Flux.fromIterable(request.getLocationIds())
                .flatMap(location -> openWeatherAPIDefault.getWeatherByLocationId(location, request.getTemperatureUnit()))
                .filter(weather -> hasTemperatureGreaterThanTomorrow(weather, request.getMinTemperature()))
                .map(WeatherResponse::getLocation)
                .map(Location::toLocationDto);
    }

    public Flux<TemperatureForecastDto> getTemperatureForecastByLocationId(WeatherForecastByLocationIdRequest request) {
        return openWeatherAPIDefault.getWeatherByLocationId(request.getLocationId(), request.getTemperatureUnit())
                .map(WeatherResponse::getLocationWeatherForecasts)
                .flatMapIterable(t -> t)
                .sort(Comparator.comparing(LocationWeather::getDate))
                .map(LocationWeather::toTemperatureForecastDto);
    }

    private boolean hasTemperatureGreaterThanTomorrow(WeatherResponse weather, Double temperature) {
        return weather.getLocationWeatherForecasts().stream()
                //.filter(lw-> lw != null)
                .anyMatch(w -> LocalDate.now().plusDays(1).getDayOfMonth() == w.getDate().getDate()
                        && w.getLocationTemperature().getTemperature() > temperature);

    }
}
