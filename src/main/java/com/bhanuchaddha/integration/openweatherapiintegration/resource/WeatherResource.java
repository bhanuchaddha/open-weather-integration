package com.bhanuchaddha.integration.openweatherapiintegration.resource;

import com.bhanuchaddha.integration.openweatherapiintegration.error.ErrorCode;
import com.bhanuchaddha.integration.openweatherapiintegration.error.exception.ValidationException;
import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.LocationDto;
import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.TemperatureForecastDto;
import com.bhanuchaddha.integration.openweatherapiintegration.service.WeatherService;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.FindLocationsByIdsAndMinTempRequest;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.FindLocationsByIdsRequest;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.WeatherForecastByLocationIdRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherResource {
    private final WeatherService weatherService;


    @GetMapping("/summary")
    public Flux<LocationDto> getLocations(
            @RequestParam(value = "minTemperature", required = false) String minTemperature
            , @RequestParam("unit") String unit
            , @RequestParam("locations") List<String> locations
    ) {

        TemperatureUnit temperatureUnit = TemperatureUnit.fromOrDefault(unit);

        log.info("getLocations was called with {}, {}, {}", locations, minTemperature, temperatureUnit);

        if (locations.isEmpty() || locations.get(0) == null) {
            throw new ValidationException(ErrorCode.AT_LEAST_ONE_LOCATION_ID_REQUIRED);
        }

        if (locations.size() > 10) {
            throw new ValidationException(ErrorCode.MAXIMUM_10_LOCATION_IDS);
        }

        if (minTemperature == null) {
            return weatherService.getLocationsByIds(FindLocationsByIdsRequest.builder()
                    .locationIds(locations)
                    .temperatureUnit(temperatureUnit)
                    .build());
        } else {
            return weatherService.getLocationsByIdsAndMinTempNextDay(FindLocationsByIdsAndMinTempRequest.builder()
                    .locationIds(locations)
                    .temperatureUnit(temperatureUnit)
                    .minTemperature(Double.valueOf(minTemperature))
                    .build());
        }
    }

    @GetMapping("/locations/{locationId}")
    public Flux<TemperatureForecastDto> getTemperatureForecast(@PathVariable("locationId") String locationId) {
        log.info("getLocations was called with {}", locationId);

        return weatherService.getTemperatureForecastByLocationId(WeatherForecastByLocationIdRequest.builder()
                .locationId(locationId)
                .temperatureUnit(TemperatureUnit.Celsius)
                .build())
                // .distinct(t-> t.getDate().getDate()) // If only single forecast per day is required
                ;
    }

}
