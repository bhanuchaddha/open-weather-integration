package com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model;

import com.bhanuchaddha.integration.openweatherapiintegration.common.UnixTimestampDeserializer;
import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.LocationDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @Getter @Setter
@Builder @AllArgsConstructor
public class Location {

    @JsonProperty("id")
    private String cityId;

    private String name;
    private String country;
    private String timezone;

    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Date sunrise;
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Date sunset;

    public LocationDto toLocationDto(){
        return LocationDto.builder()
                .cityId(cityId)
                .name(name)
                .country(country)
                .timezone(timezone)
                .sunrise(sunrise)
                .sunset(sunset)
                .build();
    }



}
