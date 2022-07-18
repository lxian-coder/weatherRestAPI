package com.darcyxian.weatherrestapi.exceptions;

/**
 * Darcy Xian  17/7/22  11:02 am      weatherRestAPI
 */
public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super(message);
    }
}
