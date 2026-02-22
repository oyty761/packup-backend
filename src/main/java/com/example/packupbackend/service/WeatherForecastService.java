package com.example.packupbackend.service;

import com.example.packupbackend.entity.WeatherForecast;
import java.time.LocalDate;
import java.util.List;

public interface WeatherForecastService {
    WeatherForecast createForecast(WeatherForecast forecast);
    WeatherForecast getForecastById(Long id);
    List<WeatherForecast> getForecastsByTripId(Long tripId);
    List<WeatherForecast> getForecastsByTripIdAndDateRange(Long tripId, LocalDate startDate, LocalDate endDate);
    List<WeatherForecast> getAllForecasts();
    WeatherForecast updateForecast(WeatherForecast forecast);
    boolean deleteForecast(Long id);
    boolean deleteForecastsByTripId(Long tripId);
    int getForecastCountByTripId(Long tripId);
}