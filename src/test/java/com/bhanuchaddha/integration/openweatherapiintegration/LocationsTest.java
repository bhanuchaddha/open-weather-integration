package com.bhanuchaddha.integration.openweatherapiintegration;

import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.LocationDto;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.hamcrest.Matchers.equalTo;

public class LocationsTest {
    @Test
    public void shouldGetLocationsHavingTemperatureMoreThan2(){
        int size = HelperMethods.getLocationsHavingMinTempTomorrow(
                HelperMethods.getlocations().subList(0,9)
                , 2
                , TemperatureUnit.Celsius)
                .body()
                .jsonPath()
                .getList(".", LocationDto.class)
                .size();
        Assert.isTrue(size > 0, "There should be location.");

    }

    @Test
    public void max10IdsAllowed(){
        HelperMethods.getLocationsHavingMinTempTomorrow(
                HelperMethods.getlocations().subList(50,70)
                , 2
                , TemperatureUnit.Celsius)
                .then()
                .statusCode(400)
                .assertThat()
                .body("errorCode", equalTo("MAXIMUM_10_LOCATION_IDS"));

    }
}
