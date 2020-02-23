package com.bhanuchaddha.integration.openweatherapiintegration.error;

import com.bhanuchaddha.integration.openweatherapiintegration.error.exception.NotFoundException;
import com.bhanuchaddha.integration.openweatherapiintegration.error.exception.ServiceException;
import com.bhanuchaddha.integration.openweatherapiintegration.error.exception.ValidationException;
import com.bhanuchaddha.integration.openweatherapiintegration.error.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {


    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Error>  handleValidationException(ValidationException exception){
        log.error(exception.getErrorCode().description, exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(400)
                        .errorCode(exception.getErrorCode().name())
                        .description(exception.getErrorCode().description)
                        .build()
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Error>  handleNotFoundException(NotFoundException exception){
        log.error(exception.getErrorCode().description, exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(404)
                        .errorCode(exception.getErrorCode().name())
                        .description(exception.getErrorCode().description)
                        .build()
                , HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<Error>  handleServiceException(ServiceException exception){
        log.error(exception.getErrorCode().description, exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(500)
                        .errorCode(exception.getErrorCode().name())
                        .description(exception.getErrorCode().description)
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error>  handleException(Exception exception){
        log.error("Unhandled Exception", exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(500)
                        .errorCode("Unhandled Exception")
                        .description(exception.getMessage())
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

//    @ExceptionHandler(WebClientResponseException.class)
//    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
//        log.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
//        return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
//    }
}
