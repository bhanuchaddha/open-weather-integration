package com.bhanuchaddha.integration.openweatherapiintegration.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class LocationDto {

    private String cityId;
    private String name;
    private String country;
    private String timezone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm")
    private Date sunrise;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm")
    private Date sunset;
}
