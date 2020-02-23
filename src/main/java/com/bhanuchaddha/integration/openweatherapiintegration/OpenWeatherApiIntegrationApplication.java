package com.bhanuchaddha.integration.openweatherapiintegration;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.common.RestIntegrationAdapter;
import com.bhanuchaddha.integration.openweatherapiintegration.integration.common.model.RestIntegrationAdapterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OpenWeatherApiIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenWeatherApiIntegrationApplication.class, args);
	}

	@Bean("openWeather")
	RestIntegrationAdapter createOpenWeatherIntegration(){
		return RestIntegrationAdapter.from(
				RestIntegrationAdapterConfig.builder()
						.baseUrl("http://api.openweathermap.org/data/2.5")
						.integrationName("openWeather")
				.build());
	}
}
