package com.bhanuchaddha.integration.openweatherapiintegration.integration.weather;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.weather.model.WeatherResponse;
import com.bhanuchaddha.integration.openweatherapiintegration.service.model.TemperatureUnit;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
public class OpenWeatherAPIDefault implements OpenWeatherAPI {

    private final WebClient webClient;
    private final String baseUrl = "http://api.openweathermap.org/data/2.5";
    private final String apiName = "openWeather";

    public OpenWeatherAPIDefault() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(logRequest())
                .build();
    }

    @Value("${open-weather.api.key}")
    private String apiKey;


    @RateLimiter(name = apiName)
    //@CircuitBreaker(name = apiName, fallbackMethod = "fallback")
    //@Bulkhead(name = apiName)
    //@Retry(name = apiName, fallbackMethod = "fallback")
    //@TimeLimiter(name = apiName)
    public Mono<WeatherResponse> getWeatherByLocationId(String locationId, TemperatureUnit temperatureUnit){
        log.info("OpenWeatherAPI is called for location {} and temperatureUnit {}", locationId, temperatureUnit.name());

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/forecast")
                        .queryParam("units", temperatureUnit.getSystem())
                        .queryParam("appid", apiKey)
                        .queryParam("id", locationId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .cache(Duration.ofHours(1)) // in-memory cache
        ;

    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }

    private Mono<WeatherResponse> fallback(String param1,TemperatureUnit temperatureUnit, RuntimeException e) {
        throw e;
    }
}
