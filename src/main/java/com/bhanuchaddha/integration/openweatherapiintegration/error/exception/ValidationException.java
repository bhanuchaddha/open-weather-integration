package com.bhanuchaddha.integration.openweatherapiintegration.error.exception;

import com.bhanuchaddha.integration.openweatherapiintegration.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private final ErrorCode errorCode;
}
