package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.WeatherForecast;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    WeatherForecast findByTripIdAndCityAndDate(@Param("tripId") Long tripId,
                                                @Param("city") String city,
                                                @Param("date") LocalDate date);
}//天气预报的插入，查找，更新，删除，统计功能
