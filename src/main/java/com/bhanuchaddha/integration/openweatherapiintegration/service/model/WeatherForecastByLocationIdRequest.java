package com.bhanuchaddha.integration.openweatherapiintegration.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class WeatherForecastByLocationIdRequest {
    private String locationId;
    private TemperatureUnit temperatureUnit;
}
