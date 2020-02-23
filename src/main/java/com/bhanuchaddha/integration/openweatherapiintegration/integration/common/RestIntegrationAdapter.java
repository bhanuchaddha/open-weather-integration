package com.bhanuchaddha.integration.openweatherapiintegration.integration.common;

import com.bhanuchaddha.integration.openweatherapiintegration.integration.common.model.RestIntegrationAdapterConfig;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Slf4j
public class RestIntegrationAdapter {

    private final WebClient webClient ;
    private final String integrationName;
    private final static String RESILIENCE_DEFAULT_CONFIGURATION = "default";

    public RestIntegrationAdapter(WebClient webClient, String integrationName) {
        this.webClient = webClient;
        this.integrationName = integrationName;
    }
    public static RestIntegrationAdapter from(RestIntegrationAdapterConfig config){
        return new RestIntegrationAdapter(
                WebClient.builder()
                        .baseUrl(config.getBaseUrl())
                        .filter(logRequest())
                        .build()
                , config.getIntegrationName()
        );
    }

    @RateLimiter(name = RESILIENCE_DEFAULT_CONFIGURATION)
    //@CircuitBreaker(name = apiName, fallbackMethod = "fallback")
    //@Bulkhead(name = apiName)
    //@Retry(name = apiName, fallbackMethod = "fallback")
    //@TimeLimiter(name = apiName)
    public <T> Mono <T> get(URI uri, String operation, Class<T> tClass){
        log.info("Integration: {} operation: {} uri: {}", this.integrationName, operation, uri.toString());
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uri.getPath())
                        .query(uri.getQuery())
                        .build()
                        )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(tClass)
                .cache(Duration.ofHours(1))
                ;
    }

    private static ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }
}
