package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TripDestination {
    private Long id;
    private Long tripId;
    private String cityName;
    private String country;
    private String poiName;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private Integer orderIndex;
}