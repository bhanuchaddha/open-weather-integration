package com.bhanuchaddha.integration.openweatherapiintegration.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor @Getter @Setter
@AllArgsConstructor
public class TemperatureForecastDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm")
    private Date date;
    private Double temperature;
    private Double feelsLike;
    private Double minTemperature;
    private Double maxTemperature;
}
