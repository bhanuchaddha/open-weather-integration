package com.bhanuchaddha.integration.openweatherapiintegration.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder @Getter
public class FindLocationsByIdsRequest {
    private List<String> locationIds;
    private TemperatureUnit temperatureUnit;
}
