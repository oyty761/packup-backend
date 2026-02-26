package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.*;
import com.example.packupbackend.mapper.*;
import com.example.packupbackend.service.DeepSeekPackingService;
import com.example.packupbackend.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl implements TripService {


    private final TripMapper tripMapper;

    private final TripDestinationMapper tripDestinationMapper;

    private final DeepSeekPackingService deepSeekPackingService;

    @Override
    @Transactional
    public void createTrip(Trip trip, List<TripDestination> destinations) {
        tripMapper.insert(trip);
        for (TripDestination dest : destinations) {
            dest.setTripId(trip.getId());
            tripDestinationMapper.insert(dest);
        }
        // 异步调用 AI 生成物品
        CompletableFuture.runAsync(() -> {
            try {
                deepSeekPackingService.generateItemsForTrip(trip);
            } catch (Exception e) {
                log.error("AI 生成物品失败", e);
            }
        });
    }
    
    @Override
    public Trip createTrip(Trip trip) {
        return null;
    }

    @Override
    public Trip getTripById(Long id) {
        return null;
    }

    @Override
    public List<Trip> getTripsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Trip> getTripsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Trip> getAllTrips() {
        return List.of();
    }

    @Override
    public Trip updateTrip(Trip trip) {
        return null;
    }

    @Override
    public boolean deleteTrip(Long id) {
        return false;
    }

    @Override
    public int getTripCountByUserId(Long userId) {
        return 0;
    }
}
