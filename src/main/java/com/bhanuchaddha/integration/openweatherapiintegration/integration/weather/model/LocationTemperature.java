package com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model;

import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.TemperatureForecastDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder @AllArgsConstructor
public class LocationTemperature {

    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("feels_like")
    private Double feelsLike;

    @JsonProperty("temp_min")
    private Double minTemperature;

    @JsonProperty("temp_max")
    private Double maxTemperature;

}
