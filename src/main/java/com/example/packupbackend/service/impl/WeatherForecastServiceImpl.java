package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.WeatherForecast;
import com.example.packupbackend.service.WeatherForecastService;
import com.example.packupbackend.mapper.WeatherForecastMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public class WeatherForecastServiceImpl implements WeatherForecastService {
    @Override
    public WeatherForecast createForecast(WeatherForecast forecast) {
        weatherForecastMapper.insert(forecast);
        return forecast;  // insert 后 forecast 的 id 会被自动回填（如果 Mapper 配置了 useGeneratedKeys）
    }

    @Override
    public WeatherForecast getForecastById(Long id) {
        return weatherForecastMapper.selectById(id);
    }

    @Override
    public List<WeatherForecast> getForecastsByTripId(Long tripId) {
        return weatherForecastMapper.selectByTripId(tripId);
    }

    @Override
    public List<WeatherForecast> getForecastsByTripIdAndDateRange(Long tripId, LocalDate startDate, LocalDate endDate) {
        return weatherForecastMapper.selectByTripIdAndDateRange(tripId, startDate, endDate);
    }

    @Override
    public List<WeatherForecast> getAllForecasts() {
        return weatherForecastMapper.selectAll();
    }

    @Override
    public WeatherForecast updateForecast(WeatherForecast forecast) {
        weatherForecastMapper.update(forecast);
        return forecast;
    }

    @Override
    public boolean deleteForecast(Long id) {
        return weatherForecastMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteForecastsByTripId(Long tripId) {
        return weatherForecastMapper.deleteByTripId(tripId) > 0;
    }

    @Override
    public int getForecastCountByTripId(Long tripId) {
        return weatherForecastMapper.countByTripId(tripId);
    }

    @Override
    public WeatherForecast getForecastByTripIdCityAndDate(Long tripId, String city, LocalDate date) {
        return weatherForecastMapper.findByTripIdAndCityAndDate(tripId, city, date);
    }
}
