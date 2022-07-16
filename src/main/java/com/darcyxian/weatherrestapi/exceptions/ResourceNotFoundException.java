package com.darcyxian.weatherrestapi.exceptions;

import lombok.NoArgsConstructor;

/**
 * Darcy Xian  16/7/22  11:40 am      weatherRestAPI
 */
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
