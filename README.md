# Open Weather API integration

## API documentation
https://documenter.getpostman.com/view/3772012/SzKVQxmd?version=latest

## Feature
* Easily navigable service package structure.
* Separation of concern using 3 layer architecture (Resource, Service, Integration)
* Using separate models for Presentation(DTO) and Internal(entity) usage.
* Centralized Exception handling using `ApplicationExceptionHandler`.
* Structured Error response for all exceptions along with correct Http status code.
* Centralized understandable error codes. `ErrorCode`.
* API testing with `RestAssured`.
* Builder Pattern is used to create immutable objects. Setters are used where ever necessary like for DTO and Marshaling/Unmarshaling.
* Logging
* Validations
* RateLimiter less than 10000 calls per day
* Reactive Programming
* Cache
* Resilient features with Resilient4j


## Further Improvement
* Java Docs
* Making filter more generic
* Better Logging
* Better Exception Handling
* Better Error handling, Specific error codes to clint to take action.
* More robust API tests
* Null checks and mandatory value validation.
* MapStruct could be used for mappers.
* Location not found should be ignored for getLocationsByIdsAndMinTempNextDay


## Technology Used
* Spring Boot
* Java 8 - streams
* Lombok
* RestAssured 
* Resilient4j

## Cache
* Cache is available at Mono/Reactor level now. Should be replaces by distributed cache like Redis
* Though refresh rate is 10 min on Open weather API but we can keep the data for 1h or 2h


## Resilience
* Resilience in the service is configured using Resilient4j.
* Only annotation and configuration required.
* Allow default and service specific configuration
* Metrics available out of the box.
* Prometheus metrics can be enabled

```json
localhost:8080/actuator/metrics/

{
    "names": [
        "jvm.memory.max",
        "jvm.threads.states",
        "resilience4j.circuitbreaker.slow.calls",
        "http.server.requests",
        "resilience4j.bulkhead.max.allowed.concurrent.calls",
        "process.files.max",
        "jvm.gc.memory.promoted",
        "resilience4j.circuitbreaker.slow.call.rate",
        "system.load.average.1m",
        "jvm.memory.used",
        "jvm.gc.max.data.size",
        "jvm.gc.pause",
        "jvm.memory.committed",
        "system.cpu.count",
        "logback.events",
        "resilience4j.timelimiter.calls",
        "jvm.buffer.memory.used",
        "jvm.threads.daemon",
        "system.cpu.usage",
        "jvm.gc.memory.allocated",
        "resilience4j.circuitbreaker.calls",
        "resilience4j.retry.calls",
        "resilience4j.circuitbreaker.state",
        "jvm.threads.live",
        "jvm.threads.peak",
        "resilience4j.circuitbreaker.failure.rate",
        "process.uptime",
        "resilience4j.circuitbreaker.buffered.calls",
        "resilience4j.ratelimiter.available.permissions",
        "process.cpu.usage",
        "jvm.classes.loaded",
        "jvm.classes.unloaded",
        "jvm.gc.live.data.size",
        "resilience4j.ratelimiter.waiting_threads",
        "process.files.open",
        "jvm.buffer.count",
        "jvm.buffer.total.capacity",
        "resilience4j.bulkhead.available.concurrent.calls",
        "process.start.time"
    ]
}
```


## Rate Limiter
* Rate limiter is configured to allow 10000 calls in 24h
* For Testing its configured to have 10 calls in 1m
* Configuration are defined in application.yaml
* After rate limit is reached exception with message "" is thrown
* Metrics of rate limiter can be observed as below
* test: com.bhanuchaddha.integration.openweatherapiintegration.ResilienceTest.callingService11TimeReturnRateLimiterError
```json
localhost:8080/actuator/metrics/resilience4j.ratelimiter.available.permissions

{
    "name": "resilience4j.ratelimiter.available.permissions",
    "description": "The number of available permissions",
    "baseUnit": null,
    "measurements": [
        {
            "statistic": "VALUE",
            "value": 7.0
        }
    ],
    "availableTags": [
        {
            "tag": "name",
            "values": [
                "openWeather"
            ]
        }
    ]
}

``` 
## CircuitBreaker, Bulkhead, Retry, TimeLimiter
* Similar to rate limiter other resilience configuration with just annotation and configuration file

## Run Service

```shell script
mvn spring-boot:run -Dspring-boot.run.profiles=test -Dspring-boot.run.arguments=--open-weather.api.key=
```

## Unit and API Testing
Unit and API test can be found in test package.

Command `mvn clean install` would have failed test because All the api tests required running service instance. Thus first start the service and then run the tests using below
```json
mvn test
```

## RestIntegrationAdapter
* To apply the resiliency features to all the rest integrations. I have implemented RestIntegrationAdapter.

* This can be used with all the future REST integrations and we can control the observability from a single place.

* com.bhanuchaddha.integration.openweatherapiintegration.integration.common.RestIntegrationAdapter

* OpenWeatherAPIRestIntegration is use RestIntegrationAdapter.
