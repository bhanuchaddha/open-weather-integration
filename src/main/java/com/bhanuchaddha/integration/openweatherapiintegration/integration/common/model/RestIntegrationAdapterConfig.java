package com.bhanuchaddha.integration.openweatherapiintegration.integration.common.model;

import lombok.Builder;
import lombok.Getter;

import java.net.URI;

@Builder @Getter
public class RestIntegrationAdapterConfig {
    private final String baseUrl;
    private final String integrationName;

}
