package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.WeatherForecast;
import com.example.packupbackend.service.WeatherForecastService;

import java.time.LocalDate;
import java.util.List;

public class WeatherForecastServiceImpl implements WeatherForecastService {
    @Override
    public WeatherForecast createForecast(WeatherForecast forecast) {
        return null;
    }

    @Override
    public WeatherForecast getForecastById(Long id) {
        return null;
    }

    @Override
    public List<WeatherForecast> getForecastsByTripId(Long tripId) {
        return List.of();
    }

    @Override
    public List<WeatherForecast> getForecastsByTripIdAndDateRange(Long tripId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<WeatherForecast> getAllForecasts() {
        return List.of();
    }

    @Override
    public WeatherForecast updateForecast(WeatherForecast forecast) {
        return null;
    }

    @Override
    public boolean deleteForecast(Long id) {
        return false;
    }

    @Override
    public boolean deleteForecastsByTripId(Long tripId) {
        return false;
    }

    @Override
    public int getForecastCountByTripId(Long tripId) {
        return 0;
    }
}
