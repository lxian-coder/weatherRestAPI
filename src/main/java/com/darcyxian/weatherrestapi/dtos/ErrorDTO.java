package com.darcyxian.weatherrestapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Darcy Xian  16/7/22  12:30 am      weatherRestAPI
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private  int cod;
    private  String message;
}
