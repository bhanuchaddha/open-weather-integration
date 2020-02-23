package com.bhanuchaddha.integration.openweatherapiintegration.error.model;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Error {
    private Integer status;
    private String errorCode;
    private String description;
}
