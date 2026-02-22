package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WeatherForecast {
    private Long id;
    private Long tripId;
    private LocalDate forecastDate;
    private String city;
    private Float minTemp;
    private Float maxTemp;
    private Float precipitation;
    private Integer humidity;
    private Integer uvIndex;
    private String weatherDesc;
    private LocalDateTime fetchTime;
}