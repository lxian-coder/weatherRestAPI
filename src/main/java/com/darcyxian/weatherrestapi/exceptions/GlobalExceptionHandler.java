package com.darcyxian.weatherrestapi.exceptions;

import com.darcyxian.weatherrestapi.dtos.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Darcy Xian  16/7/22  10:38 am      weatherRestAPI
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {InvalidInputException.class})
    public ResponseEntity<ErrorDTO> handleInvalidInputException(InvalidInputException e){
    // Here log level should not be error as this is customer input related error.
    // It's not part of system error and should not be involved in the production support.
    log.info("User input is illegal.");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorDTO(400, e.getMessage()));
}
    @ExceptionHandler(value = {NoWeatherDataException.class})
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(NoWeatherDataException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(404, e.getMessage()));
    }

}
