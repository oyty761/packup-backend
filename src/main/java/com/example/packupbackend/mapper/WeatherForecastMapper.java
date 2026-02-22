package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.WeatherForecast;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WeatherForecastMapper {
    int insert(WeatherForecast forecast);
    WeatherForecast selectById(Long id);
    List<WeatherForecast> selectByTripId(Long tripId);
    List<WeatherForecast> selectByTripIdAndDateRange(Long tripId, LocalDate startDate, LocalDate endDate);
    List<WeatherForecast> selectAll();
    int update(WeatherForecast forecast);
    int deleteById(Long id);
    int deleteByTripId(Long tripId);
    int countByTripId(Long tripId);
}