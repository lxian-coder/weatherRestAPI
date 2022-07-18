package com.darcyxian.weatherrestapi.exceptions;

/**
 * Darcy Xian  16/7/22  11:40 am      weatherRestAPI
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
