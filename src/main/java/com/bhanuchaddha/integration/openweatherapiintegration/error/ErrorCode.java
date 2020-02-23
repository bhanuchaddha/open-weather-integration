package com.bhanuchaddha.integration.openweatherapiintegration.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {

    AT_LEAST_ONE_LOCATION_ID_REQUIRED("At least 1 location id should be provided"),
    MAXIMUM_10_LOCATION_IDS("Maximum 10 location Ids can be provided"),

    ;

    String description;
}
