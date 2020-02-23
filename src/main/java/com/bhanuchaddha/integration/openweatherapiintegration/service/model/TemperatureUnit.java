package com.bhanuchaddha.integration.openweatherapiintegration.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j @Getter
public enum TemperatureUnit {
    Fahrenheit("imperial"),
    Celsius("metric");

    private String system;

    public static TemperatureUnit fromOrDefault(String tu){
        try {
            return valueOf(tu);
        }catch (IllegalArgumentException ex){
            return Celsius;
        }
    }

}
