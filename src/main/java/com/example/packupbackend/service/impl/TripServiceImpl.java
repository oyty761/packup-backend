package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.*;
import com.example.packupbackend.mapper.*;
import com.example.packupbackend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripMapper tripMapper;
    
    @Autowired
    private TripDestinationMapper tripDestinationMapper;
    
    @Autowired
    private TripActivityMapper tripActivityMapper;
    
    @Autowired
    private WeatherForecastMapper weatherForecastMapper;

    @Override
    @Transactional
    public Trip createTrip(Trip trip) {
        trip.setCreatedTime(LocalDateTime.now());
        trip.setUpdatedTime(LocalDateTime.now());
        tripMapper.insert(trip);
        return trip;
    }

    @Override
    public Trip getTripById(Long id) {
        return tripMapper.selectById(id);
    }

    @Override
    public List<Trip> getTripsByUserId(Long userId) {
        return tripMapper.selectByUserId(userId);
    }

    @Override
    public List<Trip> getTripsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return tripMapper.selectByUserIdAndDateRange(userId, startDate, endDate);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripMapper.selectAll();
    }

    @Override
    @Transactional
    public Trip updateTrip(Trip trip) {
        trip.setUpdatedTime(LocalDateTime.now());
        tripMapper.update(trip);
        return trip;
    }

    @Override
    @Transactional
    public boolean deleteTrip(Long id) {
        // 删除相关的行程目的地
        tripDestinationMapper.deleteByTripId(id);
        // 删除相关的行程活动
        tripActivityMapper.deleteByTripId(id);
        // 删除相关的天气预报
        weatherForecastMapper.deleteByTripId(id);
        // 删除行程本身
        return tripMapper.deleteById(id) > 0;
    }

    @Override
    public int getTripCountByUserId(Long userId) {
        return tripMapper.countByUserId(userId);
    }

    // 行程目的地相关方法
    @Transactional
    public TripDestination createDestination(TripDestination destination) {
        tripDestinationMapper.insert(destination);
        return destination;
    }

    public TripDestination getDestinationById(Long id) {
        return tripDestinationMapper.selectById(id);
    }

    public List<TripDestination> getDestinationsByTripId(Long tripId) {
        return tripDestinationMapper.selectByTripId(tripId);
    }

    @Transactional
    public TripDestination updateDestination(TripDestination destination) {
        tripDestinationMapper.update(destination);
        return destination;
    }

    @Transactional
    public boolean deleteDestination(Long id) {
        return tripDestinationMapper.deleteById(id) > 0;
    }

    @Transactional
    public boolean deleteDestinationsByTripId(Long tripId) {
        return tripDestinationMapper.deleteByTripId(tripId) > 0;
    }

    public int getDestinationCountByTripId(Long tripId) {
        return tripDestinationMapper.countByTripId(tripId);
    }

    // 行程活动相关方法
    @Transactional
    public TripActivity createActivity(TripActivity activity) {
        tripActivityMapper.insert(activity);
        return activity;
    }

    public TripActivity getActivityById(Long id) {
        return tripActivityMapper.selectById(id);
    }

    public List<TripActivity> getActivitiesByTripId(Long tripId) {
        return tripActivityMapper.selectByTripId(tripId);
    }

    @Transactional
    public TripActivity updateActivity(TripActivity activity) {
        tripActivityMapper.update(activity);
        return activity;
    }

    @Transactional
    public boolean deleteActivity(Long id) {
        return tripActivityMapper.deleteById(id) > 0;
    }

    @Transactional
    public boolean deleteActivitiesByTripId(Long tripId) {
        return tripActivityMapper.deleteByTripId(tripId) > 0;
    }

    public int getActivityCountByTripId(Long tripId) {
        return tripActivityMapper.countByTripId(tripId);
    }

    // 天气预报相关方法
    @Transactional
    public WeatherForecast createForecast(WeatherForecast forecast) {
        forecast.setFetchTime(LocalDateTime.now());
        weatherForecastMapper.insert(forecast);
        return forecast;
    }

    public WeatherForecast getForecastById(Long id) {
        return weatherForecastMapper.selectById(id);
    }

    public List<WeatherForecast> getForecastsByTripId(Long tripId) {
        return weatherForecastMapper.selectByTripId(tripId);
    }

    public List<WeatherForecast> getForecastsByTripIdAndDateRange(Long tripId, LocalDate startDate, LocalDate endDate) {
        return weatherForecastMapper.selectByTripIdAndDateRange(tripId, startDate, endDate);
    }

    @Transactional
    public WeatherForecast updateForecast(WeatherForecast forecast) {
        weatherForecastMapper.update(forecast);
        return forecast;
    }

    @Transactional
    public boolean deleteForecast(Long id) {
        return weatherForecastMapper.deleteById(id) > 0;
    }

    @Transactional
    public boolean deleteForecastsByTripId(Long tripId) {
        return weatherForecastMapper.deleteByTripId(tripId) > 0;
    }

    public int getForecastCountByTripId(Long tripId) {
        return weatherForecastMapper.countByTripId(tripId);
    }
}