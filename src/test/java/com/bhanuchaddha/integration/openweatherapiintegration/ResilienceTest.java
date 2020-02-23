package com.bhanuchaddha.integration.openweatherapiintegration;

import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class ResilienceTest {

    // RateLimiter would not ne triggered because we don't allow more thant 10 ids now.
    // to test this we need to disable the mox 10 id check.
    //@Test
    public void callingService11TimeReturnRateLimiterError(){
        Response response = HelperMethods.getLocationsHavingMinTempTomorrow(
                HelperMethods.getlocations().subList(0,11)
                , 2
                , TemperatureUnit.Celsius);

        response.then()
                .statusCode(500)
                .assertThat()
                .body("description", equalTo("RateLimiter 'openWeather' does not permit further calls"));
    }
}
