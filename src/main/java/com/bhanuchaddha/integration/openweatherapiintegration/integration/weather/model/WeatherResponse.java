package com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
@Builder @AllArgsConstructor
public class WeatherResponse {

    public static final WeatherResponse EMPTY = WeatherResponse.builder()
            .code(null)
            .location(null)
            .locationWeatherForecasts(Collections.emptyList())
            .build();

    @JsonProperty("cod")
    private String code;

    @JsonProperty("list")
    private List<LocationWeather> locationWeatherForecasts;

    @JsonProperty("city")
    private Location location;


}
