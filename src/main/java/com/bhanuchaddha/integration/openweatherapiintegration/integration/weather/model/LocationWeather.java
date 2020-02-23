package com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model;

import com.bhanuchaddha.integration.openweatherapiintegration.common.UnixTimestampDeserializer;
import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.TemperatureForecastDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
@Builder @AllArgsConstructor
public class LocationWeather {

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Date date;

    @JsonProperty("main")
    private LocationTemperature locationTemperature;

    public TemperatureForecastDto toTemperatureForecastDto(){
        return TemperatureForecastDto.builder()
                .date(date)
                .temperature(locationTemperature.getTemperature())
                .minTemperature(locationTemperature.getMinTemperature())
                .maxTemperature(locationTemperature.getMaxTemperature())
                .feelsLike(locationTemperature.getFeelsLike())
                .build();
    }
}
