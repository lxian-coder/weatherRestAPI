package com.darcyxian.weatherrestapi.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Darcy Xian  15/7/22  8:27 am      weatherRestAPI
 */
@Entity
@Getter
@Setter
@Table(name = "api_keys")
public class ApiKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_value")
    private String keyValue;

    @Column(name = "count_times")
    private Integer countTimes;

    @Column(name = "refresh_time")
    private BigInteger refreshTime;


}
