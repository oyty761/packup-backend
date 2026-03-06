package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.*;
import com.example.packupbackend.mapper.*;
import com.example.packupbackend.service.DeepSeekPackingService;
import com.example.packupbackend.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 创建行程
     */
    @Override
    @Transactional
    public void createTrip(Trip trip, List<TripDestination> destinations) {

        trip.setCreatedTime(LocalDateTime.now());
        trip.setUpdatedTime(LocalDateTime.now());

        // 插入 trip
        tripMapper.insert(trip);

        // 插入 destinations
        if (destinations != null) {
            for (TripDestination dest : destinations) {
                dest.setTripId(trip.getId());
                tripDestinationMapper.insert(dest);
            }
        }

        // 异步 AI 生成物品
        CompletableFuture.runAsync(() -> {
            try {
                deepSeekPackingService.generateItemsForTrip(trip);
            } catch (Exception e) {
                log.error("AI 生成物品失败", e);
            }
        });
    }

    /**
     * 创建行程（简化版本）
     */
    @Override
    public Trip createTrip(Trip trip) {

        trip.setCreatedTime(LocalDateTime.now());
        trip.setUpdatedTime(LocalDateTime.now());

        tripMapper.insert(trip);

        log.info("创建行程成功，ID: {}", trip.getId());

        return trip;
    }

    /**
     * 根据ID获取行程
     */
    @Override
    public Trip getTripById(Long id) {

        if (id == null) {
            return null;
        }

        Trip trip = tripMapper.selectById(id);

        if (trip != null) {
            List<TripDestination> destinations =
                    tripDestinationMapper.selectByTripId(id);

            trip.setDestinations(destinations);
        }

        return trip;
    }

    /**
     * 根据用户ID获取行程
     */
    @Override
    public List<Trip> getTripsByUserId(Long userId) {

        List<Trip> trips = tripMapper.selectByUserId(userId);

        for (Trip trip : trips) {

            List<TripDestination> destinations =
                    tripDestinationMapper.selectByTripId(trip.getId());

            trip.setDestinations(destinations);
        }

        return trips;
    }

    @Override
    public List<Trip> getTripsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripMapper.selectAll();
    }

    /**
     * 更新行程（重点修改）
     */
    @Override
    @Transactional
    public Trip updateTrip(Trip trip) {

        try {

            log.debug("开始更新行程，ID: {}, 名称: {}", trip.getId(), trip.getTripName());

            trip.setUpdatedTime(LocalDateTime.now());

            // 1 更新 trip 表
            int result = tripMapper.update(trip);

            if (result <= 0) {
                log.warn("行程更新失败，ID: {}", trip.getId());
                return null;
            }

            Long tripId = trip.getId();

            // 2 删除旧的目的地
            tripDestinationMapper.deleteByTripId(tripId);

            // 3 插入新的目的地
            if (trip.getDestinations() != null) {

                for (TripDestination dest : trip.getDestinations()) {

                    dest.setTripId(tripId);

                    tripDestinationMapper.insert(dest);
                }
            }

            log.info("行程更新成功，ID: {}", tripId);

            return trip;

        } catch (Exception e) {

            log.error("更新行程失败，ID: {}", trip.getId(), e);

            throw new RuntimeException("更新行程失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除行程
     */
    @Override
    @Transactional
    public boolean deleteTrip(Long id) {

        if (id == null) {
            return false;
        }

        // 先删除目的地
        tripDestinationMapper.deleteByTripId(id);

        // 再删除 trip
        int result = tripMapper.deleteById(id);

        if (result > 0) {
            log.info("行程删除成功，ID: {}", id);
            return true;
        } else {
            log.warn("行程删除失败，ID: {}", id);
            return false;
        }
    }

    @Override
    public int getTripCountByUserId(Long userId) {
        return 0;
    }
}
