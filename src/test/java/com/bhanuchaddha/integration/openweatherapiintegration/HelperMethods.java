package com.bhanuchaddha.integration.openweatherapiintegration;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.Location;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

@UtilityClass
@Slf4j
public class HelperMethods {

    private List<String> LOCATIONS = new ArrayList<>();

    @SneakyThrows
    List<String> getlocations() {
        if (LOCATIONS.isEmpty()) {
            List<Location> l = new ObjectMapper().readValue(ResourceUtils.getFile("classpath:city.list.json"), new TypeReference<List<Location>>() {
            });
            LOCATIONS.addAll(l.stream().map(Location::getCityId).collect(Collectors.toList()));
        }
        return LOCATIONS;

    }

    Response getLocationsHavingMinTempTomorrow(List<String> favoriteLocation, double minTemp, TemperatureUnit unit) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8080/weather";

        Response response = get("/summary?locations={locations}&unit={unit}&minTemperature={minTemperature}"
                , StringUtils.join(favoriteLocation, ',')
                , unit.name()
                , minTemp)
                .then()
                .extract()
                .response();

        log.info("Got Locations {} having min temp {} tomorrow", response.body().prettyPrint(), minTemp);
        return response;
    }


    Response getNextFiveDayForecastForTheLocation(String location) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8080/weather";

        Response response = get("/locations/{locationId}", location)
                .then()
                .extract()
                .response();

        log.info("Got temperature forecast {} for location {}", response.body().prettyPrint(), location);
        return response;
    }

    Response wrongUrl(List<String> favoriteLocation, double minTemp, TemperatureUnit unit) {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1:8080/wrong-weather";

        Response response = get("/summary?locations={locations}&unit={unit}&minTemperature={minTemperature}"
                , StringUtils.join(favoriteLocation, ',')
                , unit.name()
                , minTemp)
                .then()
                .extract()
                .response();

        log.info("Got Locations {} having min temp {} tomorrow", response.body().prettyPrint(), minTemp);
        return response;
    }
}
