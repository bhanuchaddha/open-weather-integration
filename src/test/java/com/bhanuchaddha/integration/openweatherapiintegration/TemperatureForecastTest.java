package com.bhanuchaddha.integration.openweatherapiintegration;

import com.bhanuchaddha.integration.openweatherapiintegration.resource.dto.TemperatureForecastDto;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class TemperatureForecastTest {

    @Test
    public void callingService4TimeReturnRateLimiterError(){
        int size = HelperMethods.getNextFiveDayForecastForTheLocation("2618425") //Copenhagen
                .body()
                .jsonPath()
                .getList(".", TemperatureForecastDto.class)
                .size();
        Assert.isTrue(size > 0, "There should be forecast for every 3 hour");
    }
}
