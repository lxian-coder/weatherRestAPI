package com.darcyxian.weatherrestapi.exceptions;

/**
 * Darcy Xian  17/7/22  11:49 pm      weatherRestAPI
 */

public class NoWeatherDataException extends RuntimeException {
    public NoWeatherDataException(String message) {
        super(message);
    }

}
